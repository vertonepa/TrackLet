package com.vertonepa.tracklet.tickets.presentation.ticketlogs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vertonepa.tracklet.navigation.graphs.TicketLogsDestination
import com.vertonepa.tracklet.tickets.domain.model.TicketLog
import com.vertonepa.tracklet.tickets.domain.model.enums.PaymentState
import com.vertonepa.tracklet.tickets.domain.repository.TicketLogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TicketLogsViewModel @Inject constructor(
    private val ticketLogsRepository: TicketLogsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var selectedItems by mutableStateOf(emptySet<Int>())
        private set

    var isMultiSelectionEnabled by mutableStateOf(false)
        private set

    private val ticketLogsRoute: TicketLogsDestination = savedStateHandle.toRoute()
    private val _ticketId = savedStateHandle.getStateFlow(
        key = "ticketIdKey",
        initialValue = ticketLogsRoute.id
    ).value
    val ticketId: Int = _ticketId

    val uiState =
        ticketLogsRepository.getLogs(_ticketId)
            .map { ticketLogs ->
                if (ticketLogs.isEmpty()) TicketLogsUIState.Empty
                else TicketLogsUIState.Success(ticketLogs = ticketLogs)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TicketLogsUIState.Loading
            )

    val totalsState = ticketLogsRepository
        .getTotals(_ticketId)
        .map { it.toUi() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiTotals()
        )

    fun onGenerateLog(
        paymentState: PaymentState,
        date: LocalDate,
        quantity: Int,
        color: String
    ) {
        viewModelScope.launch {
            ticketLogsRepository.generateLog(
                TicketLog(
                    ticketId = ticketId,
                    paymentState = paymentState.state,
                    date = date,
                    quantity = quantity,
                    color = color
                )
            )
        }
    }

    fun onDeleteLog(ids: Set<Int>) {
        viewModelScope.launch {
            ticketLogsRepository.logDelete(ids)
        }
    }

    fun onItemClick(id: Int) {
        selectedItems = when {
            isMultiSelectionEnabled && (id in selectedItems) -> {
                selectedItems - id
            }

            isMultiSelectionEnabled && id !in selectedItems -> {
                selectedItems + id
            }

            else -> setOf(id)
        }
    }

    fun onItemLongPress(id: Int) {
        isMultiSelectionEnabled = true
        selectedItems = setOf(id)

    }

    fun onUndoLongPress() {
        isMultiSelectionEnabled = false
        selectedItems = emptySet()
    }

    fun onMarkLog(id: Int) {
        if (isMultiSelectionEnabled) false
        selectedItems = setOf(id)
    }

}

