package com.vertonepa.tracklet.timecounter.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.vertonepa.tracklet.R
import com.vertonepa.tracklet.core.ui.TrackletIcons
import com.vertonepa.tracklet.timecounter.data.TimecounterRepository
import com.vertonepa.tracklet.timecounter.presentation.utils.Time
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterState
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.NOTIFICATION_ID
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.SERVICE_NOTIFICATION_CHANNEL_ID
import com.vertonepa.tracklet.timecounter.presentation.utils.formatToString
import com.vertonepa.tracklet.timecounter.presentation.utils.totalInSeconds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@AndroidEntryPoint
class TimecounterService : Service() {
    @Inject
    lateinit var repository: TimecounterRepository
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private var duration: Duration = Duration.ZERO
    private val binder = TimeCounterBinder(this)
    private var lastTickTimestamp: Long = 0L
    private var counterJob: Job? = null

    private val _timecounterId = MutableStateFlow<Int?>(null)
    private val timecounterId: Int get() = _timecounterId.value ?: 0

    private val _time = MutableStateFlow(Time())
    val time = _time.asStateFlow()

    private val _isTimecounterPaused = MutableStateFlow(false)
    private val isTimecounterPaused = _isTimecounterPaused.asStateFlow()

    private val _isTimecounterActive = MutableStateFlow(false)
    val isTimecounterActive = _isTimecounterActive.asStateFlow()

    private val _currentState = MutableStateFlow(TimecounterState.NOT_INITIALIZED)
    val currentState = _currentState.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val timecounter = _timecounterId.flatMapLatest { id ->
        if (id == null || id == 0) {
            flowOf(null)
        } else {
            repository.getTimecounter(id)
        }
    }.stateIn(serviceScope, SharingStarted.Eagerly, null)

    private val ticketId: Int get() = timecounter.value?.ticketId!!


    override fun onCreate() {
        super.onCreate()
        notificationBuilder = NotificationCompat.Builder(this, SERVICE_NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Tracklet")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setDefaults(0)

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onStartCommand(
        intent: Intent?, flags: Int, startId: Int
    ): Int {

        val action = intent?.action ?: intent?.getStringExtra(TimecounterValues.TIMECOUNTER_STATE)

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

    fun connectWithTimecounterById(timecounterId: Int) {
        _timecounterId.value = timecounterId
    }

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

    private fun startTimecounter() {
        _currentState.value = TimecounterState.RESUMED
        _isTimecounterPaused.value = false
        _isTimecounterActive.value = true
    }

    private fun resumeTimecounter(onTick: (timecounter: Time) -> Unit) {
        counterJob?.cancel()
        counterJob = null

        _currentState.value = TimecounterState.RESUMED
        _isTimecounterPaused.value = false
        changeNotificationButton(isTimecounterPaused.value)

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

        changeNotificationButton(isTimecounterPaused.value)
    }

    private fun stopTimecounter() {
        _currentState.value = TimecounterState.NOT_INITIALIZED
        _isTimecounterActive.value = false

        counterJob?.cancel()
        counterJob = null

        serviceScope.launch {
            repository.saveTimeElapsed(timecounterId, _time.value.totalInSeconds())
            repository.changeActiveState(timecounterId, isTimecounterActive.value)
        }

        lastTickTimestamp = 0L
        duration = Duration.ZERO
        updateTime()
    }

    private fun cancelTimecounter() {
        _currentState.value = TimecounterState.NOT_INITIALIZED
        _isTimecounterActive.value = false

        counterJob?.cancel()
        counterJob = null

        lastTickTimestamp = 0L
        duration = Duration.ZERO
        updateTime()

        serviceScope.launch {
            repository.deleteTimecounter(timecounterId)
        }
    }


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

    private fun changeNotificationButton(isPaused: Boolean) {
        Log.d("TimecounterService", "private val ticketId: Int get() = $ticketId")
        notificationBuilder.clearActions()
        notificationBuilder.setContentIntent(TCServiceHelper.clickNotification(this, ticketId, timecounterId))
        notificationBuilder.addAction(
            TrackletIcons.StopNotif,
            TimecounterValues.STOP,
            TCServiceHelper.stop(this, ticketId, timecounterId)
        )
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
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }


    class TimeCounterBinder(service: TimecounterService) : Binder() {
        private val serviceRef = WeakReference(service)
        fun getService(): TimecounterService? = serviceRef.get()
    }
}
