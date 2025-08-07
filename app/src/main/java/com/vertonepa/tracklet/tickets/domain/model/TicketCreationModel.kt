package com.vertonepa.tracklet.tickets.domain.model

import com.vertonepa.tracklet.tickets.domain.model.enums.TicketTaskProgressLabel
import java.time.LocalDate
import java.util.UUID

data class TicketCreationModel(
    val ticketId: String = UUID.randomUUID().toString(),
    val ticketHeading: String,
    val ticketDescription: String,
    val ticketPublishDate: LocalDate? = LocalDate.now(),
    val paymentStatus: String? = null,
    val orderByNumber: Long = System.currentTimeMillis(),
    val ticketTaskProgress: String = TicketTaskProgressLabel.CREATED.status,
)
