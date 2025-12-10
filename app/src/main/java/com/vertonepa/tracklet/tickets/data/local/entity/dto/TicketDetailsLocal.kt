package com.vertonepa.tracklet.tickets.data.local.entity.dto

import androidx.room.ColumnInfo
import java.time.LocalDate

data class TicketDetailsLocal(
    @ColumnInfo(name = "ticket_id") val ticketId: Int,
    @ColumnInfo(name = "ticket_heading") val ticketHeading: String,
    @ColumnInfo(name = "ticket_description") val ticketDescription: String,
    @ColumnInfo(name = "ticket_publish_date") val ticketPublishDate: LocalDate,
    @ColumnInfo(name = "ticket_task_progress") val ticketTaskProgress: String,
    @ColumnInfo(name = "payment_state") val paymentState: String
)

