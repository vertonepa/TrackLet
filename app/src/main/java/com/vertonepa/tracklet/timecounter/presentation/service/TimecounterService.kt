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
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.NOTIFICATION_CHANNEL_ID
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.NOTIFICATION_ID
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.TC_STATE
import com.vertonepa.tracklet.timecounter.presentation.utils.formatToString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class TimecounterService : Service() {
    @Inject
    lateinit var repository: TimecounterRepository
    private lateinit var timer: Timer
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private var duration: Duration = Duration.ZERO
    private val binder = TimeCounterBinder()

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
        notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Tracklet")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
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

    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onStartCommand(
        intent: Intent?, flags: Int, startId: Int
    ): Int {
        //ramas para notif
        when (intent?.getStringExtra(TC_STATE)) {
            TimecounterState.RESUMED.name -> {
                startForegroundService()
                resumeTimecounter { timecounter ->
                    updateNotification(timecounter)
                }
            }

            TimecounterState.PAUSED.name -> {
                pauseTimecounter()
            }

            TimecounterState.STOPPED.name -> {
                stopTimecounter()
                stopForegroundService()
            }

            TimecounterState.CANCELED.name -> {
                TCServiceHelper.cancel(this)
            }

            else -> {}
        }
        //ramas para acciones en pantalla
        intent?.action.let {
            when (it) {
                TimecounterValues.START -> {
                    startTimecounter()
                }

                TimecounterValues.RESUME -> {
                    startForegroundService()
                    resumeTimecounter { timecounter ->
                        updateNotification(timecounter)
                    }
                }

                TimecounterValues.PAUSE -> {
                    pauseTimecounter()
                }

                TimecounterValues.STOP -> {
                    stopTimecounter()
                    stopForegroundService()
                }

                TimecounterValues.CANCEL -> {
                    stopForegroundService()
                }
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
        _currentState.value = TimecounterState.ACTIVE
        _isTimecounterActive.value = true
        _isTimecounterPaused.value = false

        /*crea un nuevo timecounter
        *cambia el estado a Resume -> TimecounterState.RESUMED ✅
        *activa el contador -> currentState = TimecounterState.ACTIVE && isActive = true ✅
        * El Repository debe actualizar isActive a true
        * avisa que el timecounter no está pausado para que se ajuste el icono correcto ✅
        *icono de screen debe cambiar a Resume Icon
        *mensaje en screen que avise que el contador está activado
        *no vuelve a llamar esta funcion hasta que STOP o CANCEL actualicen isActive = false
         */
    }

    private fun resumeTimecounter(onTick: (timecounter: Time) -> Unit) {
        _currentState.value = TimecounterState.RESUMED
        _isTimecounterPaused.value = false
        changeNotifButton(isTimecounterPaused.value)

        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.plus(1.seconds)
            updateTime()
            onTick(_time.value)
        }
    }

    private fun pauseTimecounter() {
        _currentState.value = TimecounterState.PAUSED
        _isTimecounterPaused.value = true
        changeNotifButton(isTimecounterPaused.value)

        if (this::timer.isInitialized) {
            timer.cancel()
        }
    }

    private fun stopTimecounter() {
        _currentState.value = TimecounterState.STOPPED
        _isTimecounterActive.value = false

        timer.cancel()
        duration = Duration.ZERO
        updateTime()

        //detiene el contador ✅, reinicia a 00:00:00 ✅, almacena los datos localmente antes de reiniciar a 0
        //se registra en Logs y devuelve a TicketDetailsScreen
        //permite abrir un nuevo contador con nuevo id
    }

    private fun cancelTimecounter() {
        _currentState.value = TimecounterState.CANCELED
        _isTimecounterActive.value = false
        duration = Duration.ZERO

        //detiene el servicio, elimina los datos almacenados del Timecounter, reinicia a 00:00:00 ✅
        //permite abrir un nuevo contador con nuevo id

    }


    //Notif ////////////////////////////////////////////
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun updateTime() {
        duration.toComponents { hours, minutes, seconds, _ ->
            _time.value = Time(hours.toInt(), minutes, seconds)
        }
    }

    private fun updateNotification(timecounter: Time) {
        notificationManager.notify(
            NOTIFICATION_ID, notificationBuilder.setContentText(
                timecounter.formatToString()
            ).build()
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


