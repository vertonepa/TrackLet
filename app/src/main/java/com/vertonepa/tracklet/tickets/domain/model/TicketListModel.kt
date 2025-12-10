package com.vertonepa.tracklet.tickets.domain.model

import java.time.LocalDate

data class TicketListModel(
    val ticketId: Int,
    val ticketHeading: String,
    val ticketPublishDate: LocalDate,
)
