package com.vertonepa.tracklet.timecounter.presentation.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.vertonepa.tracklet.timecounter.presentation.utils.Time
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class ServiceConnector @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _service = MutableStateFlow<TimecounterService?>(null)
    val service = _service.asStateFlow()

    val time = _service.flatMapLatest { service ->
        service?.time ?: flowOf(Time())
    }

    val timecounter = _service.flatMapLatest { service ->
        service?.currentState ?: flowOf(TimecounterState.IDLE)
    }

    val isActive = service.flatMapLatest {service ->
        service?.isTimecounterActive ?: flowOf(false)
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            binder: IBinder?
        ) {
            val binder = binder as TimecounterService.TimeCounterBinder
            _service.value = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            _service.value = null
        }
    }

    fun bind() {
        val intent = Intent(context, TimecounterService::class.java)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbind() {
        context.unbindService(connection)
        _service.value = null
    }
}