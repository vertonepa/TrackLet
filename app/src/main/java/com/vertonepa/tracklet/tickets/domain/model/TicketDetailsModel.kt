package com.vertonepa.tracklet.tickets.domain.model

import java.time.LocalDate

data class TicketDetailsModel(
    val ticketId: Int,
    val ticketHeading: String,
    val ticketDescription: String,
    val paymentState: String,
    val ticketTaskProgress: String,
    val ticketPublishDate: LocalDate,
    val pictures: List<String>
)