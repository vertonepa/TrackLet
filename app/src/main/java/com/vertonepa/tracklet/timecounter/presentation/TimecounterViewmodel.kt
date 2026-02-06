package com.vertonepa.tracklet.timecounter.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vertonepa.tracklet.navigation.graphs.TimecounterDestination
import com.vertonepa.tracklet.timecounter.presentation.service.ServiceConnector
import com.vertonepa.tracklet.timecounter.presentation.utils.Time
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TimecounterViewmodel @Inject constructor(
    private val connector: ServiceConnector,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val timecounterRoute: TimecounterDestination = savedStateHandle.toRoute()
    val timecounterId =
        savedStateHandle.getStateFlow("timecounterId", timecounterRoute.timecounterId).value

    val timeState = connector.time.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Time()
    )

    val timecounterState = connector.timecounterState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TimecounterState.NOT_INITIALIZED
    )

    val uiState: StateFlow<TimecounterUiState> = connector.timecounter.map { timecounterFromService ->
        Log.d("TimecounterViewmodel", "timecounter recibido: $timecounterFromService")
        if (timecounterFromService == null) {
            TimecounterUiState.Loading
        } else {
            TimecounterUiState.Success(timecounterFromService)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TimecounterUiState.Loading
    )

    init {
        Log.d("TimecounterViewmodel", "ID recibido en timecounterviewmodel: $timecounterId")
        connector.connectWithTimecounterById(timecounterId)
        connector.bind()
    }

    override fun onCleared() {
        connector.unbind()
        super.onCleared()
    }
}