package com.vertonepa.tracklet.timecounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertonepa.tracklet.timecounter.presentation.service.ServiceConnector
import com.vertonepa.tracklet.timecounter.presentation.utils.Time
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TimecounterViewmodel @Inject constructor(
    private val connector: ServiceConnector
) : ViewModel() {
    val timeState = connector.time
        .sharingStartedWhileSubscribed(Time())

    val timecounterState = connector.timecounter
        .sharingStartedWhileSubscribed(TimecounterState.IDLE)

    val isActive = connector.isActive.sharingStartedWhileSubscribed(false)

    fun <T> Flow<T>.sharingStartedWhileSubscribed(initialValue: T): StateFlow<T> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initialValue
        )
    }

    init {
        connector.bind()
    }

    override fun onCleared() {
        connector.unbind()
        super.onCleared()
    }
}