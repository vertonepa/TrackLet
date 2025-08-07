package com.vertonepa.tracklet.tickets.domain.model

data class TicketDetailsModel(
    val ticketId: String,
    val ticketHeading: String,
    val ticketDescription: String,
    val paymentStatus: String?,
    val ticketTaskProgress: String,
)