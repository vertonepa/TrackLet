package com.vertonepa.tracklet.timecounter.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.vertonepa.tracklet.R
import com.vertonepa.tracklet.core.ui.TrackletIcons
import com.vertonepa.tracklet.timecounter.data.TimecounterRepository
import com.vertonepa.tracklet.timecounter.presentation.utils.Time
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterState
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.SERVICE_NOTIFICATION_CHANNEL_ID
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.NOTIFICATION_ID
import com.vertonepa.tracklet.timecounter.presentation.utils.formatToString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@AndroidEntryPoint
class TimecounterService : Service() {
    @Inject
    lateinit var repository: TimecounterRepository
    val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private var duration: Duration = Duration.ZERO
    private val binder = TimeCounterBinder()
    private var lastTickTimestamp: Long = 0L
    private var counterJob: Job? = null
    private var ticketId: Int = -1
    private var tcId: Int = -1

    private val _time = MutableStateFlow(Time())
    val time = _time.asStateFlow()

    private val _isTimecounterPaused = MutableStateFlow(false)
    private val isTimecounterPaused = _isTimecounterPaused.asStateFlow()

    private val _isTimecounterActive = MutableStateFlow(false)
    val isTimecounterActive = _isTimecounterActive.asStateFlow()

    private val _currentState = MutableStateFlow(TimecounterState.IDLE)
    val currentState = _currentState.asStateFlow()


    override fun onCreate() {
        super.onCreate()
        notificationBuilder = NotificationCompat.Builder(this, SERVICE_NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Tracklet")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setSilent(true)
            .setDefaults(0)
            .addAction(
                TrackletIcons.ResumeNotif,
                TimecounterValues.RESUME,
                TCServiceHelper.resume(this)
            )
            .addAction(
                TrackletIcons.PauseNotif,
                TimecounterValues.PAUSE,
                TCServiceHelper.pause(this)
            )
            .addAction(TrackletIcons.StopNotif, TimecounterValues.STOP, TCServiceHelper.stop(this))
            .setContentIntent(TCServiceHelper.clickNotification(this))

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onStartCommand(
        intent: Intent?, flags: Int, startId: Int
    ): Int {
        ticketId = intent?.getIntExtra(TimecounterValues.TICKET_ID_EXTRA, -1) ?: ticketId
        tcId = intent?.getIntExtra("", -1) ?: tcId

        val action = intent?.action ?: intent?.getStringExtra(TimecounterValues.TC_STATE)

        when (action) {
            TimecounterValues.START -> {
                startForegroundService()
                startTimecounter()
                resumeTimecounter { timecounter ->
                    updateNotification(timecounter)
                }
            }

            TimecounterValues.RESUME, TimecounterState.RESUMED.name -> {
                startForegroundService()
                resumeTimecounter { timecounter ->
                    updateNotification(timecounter)
                }
            }

            TimecounterValues.PAUSE, TimecounterState.PAUSED.name -> {
                pauseTimecounter()
            }

            TimecounterValues.STOP, TimecounterState.STOPPED.name -> {
                stopTimecounter()
                stopForegroundService()
            }

            TimecounterValues.CANCEL, TimecounterState.CANCELED.name -> {
                cancelTimecounter()
                stopForegroundService()
            }
        }

        return START_STICKY
    }

    //F.Service //////////////////////////////////////////////////
    private fun startForegroundService() {
        createNotificationChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NOTIFICATION_ID,
                notificationBuilder.build(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    //Actions ////////////////////////////////////////////
    private fun startTimecounter() {
        _currentState.value = TimecounterState.RESUMED
        _isTimecounterActive.value = true
        _isTimecounterPaused.value = false

//        serviceScope.launch {
//            repository.activeState(tcId, isTimecounterActive.value)
//        }

        /*crea un nuevo timecounter
        *cambia el estado a Resume -> TimecounterState.RESUMED ✅
        *activa el contador -> currentState = TimecounterState.RESUMED && isActive = true ✅
        * El Repository debe actualizar isActive a true
        * avisa que el timecounter no está pausado para que se ajuste el icono correcto ✅
        *icono de screen debe cambiar a Resume Icon
        *mensaje en screen que avise que el contador está activado
        *no vuelve a llamar esta funcion hasta que STOP o CANCEL actualicen isActive = false
         */
    }

    private fun resumeTimecounter(onTick: (timecounter: Time) -> Unit) {
        counterJob?.cancel()
        counterJob = null

        _currentState.value = TimecounterState.RESUMED
        _isTimecounterPaused.value = false
        changeNotifButton(isTimecounterPaused.value)

        counterJob = serviceScope.launch {
            lastTickTimestamp = System.currentTimeMillis()

            while (isActive && isTimecounterActive.value) {
                delay(1000)
                val now = System.currentTimeMillis()
                val elapsedTime = now - lastTickTimestamp
                duration = duration.plus(elapsedTime.toDuration(DurationUnit.MILLISECONDS))
                lastTickTimestamp = now
                updateTime()
                onTick(_time.value)
            }
        }
    }

    private fun pauseTimecounter() {
        _currentState.value = TimecounterState.PAUSED
        _isTimecounterPaused.value = true

        if (lastTickTimestamp > 0) {
            val now = System.currentTimeMillis()
            val elapsedTime = now - lastTickTimestamp
            duration = duration.plus(elapsedTime.toDuration(DurationUnit.MILLISECONDS))
            updateTime()
        }

        counterJob?.cancel()
        counterJob = null
        lastTickTimestamp = 0L

        changeNotifButton(isTimecounterPaused.value)
    }

    private fun stopTimecounter() {
        _currentState.value = TimecounterState.IDLE
        _isTimecounterActive.value = false

        counterJob?.cancel()
        counterJob = null

        lastTickTimestamp = 0L
        duration = Duration.ZERO
        updateTime()

        //detiene el contador ✅, reinicia a 00:00:00 ✅, almacena los datos localmente antes de reiniciar a 0
        //se registra en Logs y devuelve a TicketDetailsScreen
        //permite abrir un nuevo contador con nuevo id
    }

    private fun cancelTimecounter() {
        _currentState.value = TimecounterState.IDLE
        _isTimecounterActive.value = false

        counterJob?.cancel()
        counterJob = null

        lastTickTimestamp = 0L
        duration = Duration.ZERO
        updateTime()

        //detiene el servicio
        // elimina el Timecounter, reinicia a 00:00:00 ✅
        //permite abrir un nuevo contador con nuevo id

    }


    //Notif ////////////////////////////////////////////
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            SERVICE_NOTIFICATION_CHANNEL_ID,
            SERVICE_NOTIFICATION_CHANNEL_ID,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Canal de contador de tiempo de Tracklet"
            setSound(null, null)
            enableVibration(false)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            setShowBadge(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun updateTime() {
        duration.toComponents { hours, minutes, seconds, _ ->
            _time.value = Time(hours.toInt(), minutes, seconds)
        }
    }

    private fun updateNotification(timecounter: Time) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder
                .setContentText(timecounter.formatToString())
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build()
        )
    }

    private fun changeNotifButton(isPaused: Boolean) {
        notificationBuilder.clearActions()
        if (isPaused) {
            notificationBuilder.addAction(
                TrackletIcons.ResumeNotif,
                TimecounterValues.RESUME,
                TCServiceHelper.resume(this)
            )
        } else {
            notificationBuilder.addAction(
                TrackletIcons.PauseNotif,
                TimecounterValues.PAUSE,
                TCServiceHelper.pause(this)
            )
        }

        notificationBuilder.addAction(
            TrackletIcons.StopNotif,
            TimecounterValues.STOP,
            TCServiceHelper.stop(this)
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }


    inner class TimeCounterBinder() : Binder() {
        fun getService(): TimecounterService = this@TimecounterService
    }
}


