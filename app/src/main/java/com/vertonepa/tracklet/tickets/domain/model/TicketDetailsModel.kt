package com.vertonepa.tracklet.tickets.domain.model

import com.vertonepa.tracklet.tickets.data.local.entity.dto.TicketDetailsLocal
import java.time.LocalDate

data class TicketDetailsModel(
    val ticketId: String,
    val ticketHeading: String,
    val ticketDescription: String,
    val paymentStatus: String,
    val ticketTaskProgress: String,
    val ticketPublishDate: LocalDate
)

fun TicketDetailsModel.toDetailsLocal(): TicketDetailsLocal {
    return TicketDetailsLocal(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketDescription = this.ticketDescription,
        paymentStatus = this.paymentStatus,
        ticketTaskProgress = this.ticketTaskProgress,
        ticketPublishDate = this.ticketPublishDate,
    )
}