package com.vertonepa.tracklet.tickets.domain.model

import com.vertonepa.tracklet.tickets.domain.model.enums.PaymentStatus
import java.time.LocalDate
import java.util.UUID

data class TicketLog(
    val logId: Int = -1,
    val ticketId: UUID,
    val paymentState: String = PaymentStatus.OWES.status,
    val date: LocalDate,
    val quantity: Int,
    val color: String,
    val hasMark: Boolean = false
)
