package com.vertonepa.tracklet.tickets.presentation.ticket_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.usecases.DeleteTicketByIdUseCase
import com.vertonepa.tracklet.tickets.domain.usecases.GetTicketDetailsUseCase
import com.vertonepa.tracklet.tickets.domain.usecases.GetTicketsUseCase
import com.vertonepa.tracklet.tickets.presentation.ticket_list.TicketListUIState.Tickets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketListViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase,
    private val deleteTicketByIdUseCase: DeleteTicketByIdUseCase,
    private val getTicketDetailsUseCase: GetTicketDetailsUseCase,
) : ViewModel() {
    val uiState: StateFlow<TicketListUIState> = getTicketsUseCase()
        .map(::Tickets)
        .catch { TicketListUIState.EmptyList }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TicketListUIState.Loading
        )

    private val _tickets = MutableStateFlow<List<TicketListModel>>(emptyList())
    val tickets: StateFlow<List<TicketListModel>> = _tickets


    fun onDeleteTicket(id: String) {
        viewModelScope.launch {
            val ticket = getTicketDetailsUseCase(id = id)
            deleteTicketByIdUseCase(ticket)
        }
    }
}

sealed class TicketListUIState() {
    data object Loading : TicketListUIState()

    data object EmptyList : TicketListUIState()

    data class Tickets(val tickets: List<TicketListModel>) : TicketListUIState()
}