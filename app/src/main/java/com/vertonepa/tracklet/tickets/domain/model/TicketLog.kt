package com.vertonepa.tracklet.tickets.domain.model

import com.vertonepa.tracklet.tickets.domain.model.enums.PaymentState
import java.time.LocalDate
import java.util.UUID

data class TicketLog(
    val logId: Int = 0,
    val ticketId: Int,
    val paymentState: String = PaymentState.OWES.state,
    val date: LocalDate,
    val quantity: Int,
    val color: String,
    val hasMark: Boolean = false
)
