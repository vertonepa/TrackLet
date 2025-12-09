package com.vertonepa.tracklet.tickets.domain.model

import com.vertonepa.tracklet.tickets.domain.model.enums.PaymentState
import com.vertonepa.tracklet.tickets.domain.model.enums.TicketTaskProgressLabel
import java.time.LocalDate

data class TicketCreationModel(
    val ticketId: Int = 0,
    val ticketHeading: String,
    val ticketDescription: String,
    val ticketPublishDate: LocalDate = LocalDate.now(),
    val paymentState: String = PaymentState.NOT_APPLICABLE.state,
    val ticketTaskProgress: String = TicketTaskProgressLabel.CREATED.state,
)

