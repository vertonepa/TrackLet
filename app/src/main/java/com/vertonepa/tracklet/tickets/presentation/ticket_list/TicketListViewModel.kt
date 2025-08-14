package com.vertonepa.tracklet.tickets.presentation.ticket_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.usecases.DeleteTicketByIdUseCase
import com.vertonepa.tracklet.tickets.domain.usecases.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketListViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase,
    private val deleteTicketByIdUseCase: DeleteTicketByIdUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<TicketListUIState>(TicketListUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadTickets()
    }

    fun loadTickets() {
        viewModelScope.launch {
            getTicketsUseCase().collectLatest { tickets ->
                _uiState.value = tickets.takeIf { it.isNotEmpty() }
                    ?.let { TicketListUIState.Success(it) }
                    ?: TicketListUIState.EmptyList
            }
        }
    }

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