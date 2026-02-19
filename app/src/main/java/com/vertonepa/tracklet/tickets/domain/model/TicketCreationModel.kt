package com.vertonepa.tracklet.tickets.domain.model

import com.vertonepa.tracklet.tickets.domain.model.enums.PaymentState
import com.vertonepa.tracklet.tickets.domain.model.enums.TicketTaskProgressLabel
import java.time.LocalDate

data class TicketCreationModel(
    val ticketId: Int = 0,
    val heading: String,
    val description: String,
    val pictures: List<String> = emptyList(),
    val publishDate: LocalDate = LocalDate.now(),
    val paymentState: String = PaymentState.NOT_APPLICABLE.state,
    val taskProgress: String = TicketTaskProgressLabel.CREATED.state,
)

