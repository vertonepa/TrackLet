package com.vertonepa.tracklet.tickets.presentation.ticketlogs

import com.vertonepa.tracklet.tickets.domain.model.TicketLog

sealed class TicketLogsUIState() {
    data object Loading : TicketLogsUIState()
    data class Success(val ticketLogs: List<TicketLog>) : TicketLogsUIState()
    data object Empty : TicketLogsUIState()
}