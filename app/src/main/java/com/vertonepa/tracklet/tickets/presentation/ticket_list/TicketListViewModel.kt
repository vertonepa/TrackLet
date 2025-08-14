package com.vertonepa.tracklet.tickets.presentation.ticket_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.usecases.DeleteTicketByIdUseCase
import com.vertonepa.tracklet.tickets.domain.usecases.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketListViewModel @Inject constructor(
    getTicketsUseCase: GetTicketsUseCase,
    private val deleteTicketByIdUseCase: DeleteTicketByIdUseCase,
) : ViewModel() {
    private val _uiState =
        getTicketsUseCase()
            .map { tickets ->
                if (tickets.isEmpty()) TicketListUIState.EmptyList
                else TicketListUIState.Success(tickets)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TicketListUIState.Loading
            )

    val uiState: StateFlow<TicketListUIState> = _uiState

    fun onDeleteTicket(id: String) {
        viewModelScope.launch {
            deleteTicketByIdUseCase(id)
        }
    }
}

sealed class TicketListUIState() {
    data object Loading : TicketListUIState()

    data object EmptyList : TicketListUIState()

    data class Success(val tickets: List<TicketListModel>) : TicketListUIState()
}