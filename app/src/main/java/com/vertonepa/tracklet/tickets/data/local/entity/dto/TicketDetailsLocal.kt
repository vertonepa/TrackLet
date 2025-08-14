package com.vertonepa.tracklet.tickets.data.local.entity.dto

import androidx.room.ColumnInfo
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import java.time.LocalDate

data class TicketDetailsLocal(
    @ColumnInfo(name = "ticket_id") val ticketId: String,
    @ColumnInfo(name = "ticket_heading") val ticketHeading: String,
    @ColumnInfo(name = "ticket_description") val ticketDescription: String,
    @ColumnInfo(name = "ticket_publish_date") val ticketPublishDate: LocalDate,
    @ColumnInfo(name = "ticket_task_progress") val ticketTaskProgress: String,
    @ColumnInfo(name = "payment_status") val paymentStatus: String
)

fun TicketDetailsLocal.toTicketDetailsModel(): TicketDetailsModel {
    return TicketDetailsModel(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketDescription = this.ticketDescription,
        paymentStatus = this.paymentStatus,
        ticketTaskProgress = this.ticketTaskProgress,
        ticketPublishDate = this.ticketPublishDate
    )
}