package com.vertonepa.tracklet.tickets.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vertonepa.tracklet.navigation.graphs.DetailsDestination
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import com.vertonepa.tracklet.tickets.domain.model.Timecounter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: TicketsRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val detailsRoute: DetailsDestination = savedStateHandle.toRoute()
    private val ticketId = savedStateHandle.getStateFlow(
        key = "ticketIdKey", initialValue = detailsRoute.ticketId
    ).value

    private val _uiState = MutableStateFlow<DetailsUIState>(DetailsUIState.Loading)
    val uiState = repository.getTicketDetailsById(ticketId)
        .map { DetailsUIState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailsUIState.Loading
        )

    val isThereAnActiveTimecounterOnThisTicket: StateFlow<Boolean> =
        repository.getTicketIdFromTheActiveTimecounter()
            .map { ticketIdFromTheActiveTimecounter -> ticketIdFromTheActiveTimecounter == ticketId }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )

    val isThereAnActiveTimecounterInTheApp: StateFlow<Boolean> =
        repository.getTicketIdFromTheActiveTimecounter()
            .map { ticketIdFromTheActiveTimecounter ->
                ticketIdFromTheActiveTimecounter != 0 && ticketIdFromTheActiveTimecounter != ticketId
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )

    val timecounterId = repository
        .getTimecounterId(ticketId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    private var detailsJob: Job? = null

    fun onClickInitNewTimecounter(ticketId: Int) {
        viewModelScope.launch {
            repository.initNewTimecounter(Timecounter(ticketId))
        }
    }

    fun onClickDelete(id: Int) {
        viewModelScope.launch {
            repository.deleteTicketById(id)
            onDelete()
        }
    }

    private fun onDelete() {
        detailsJob?.cancel()
        _uiState.value = DetailsUIState.Error
    }

}

sealed class DetailsUIState {
    data object Loading : DetailsUIState()
    data class Success(val ticketDetails: TicketDetailsModel) : DetailsUIState()
    data object Error : DetailsUIState()
}