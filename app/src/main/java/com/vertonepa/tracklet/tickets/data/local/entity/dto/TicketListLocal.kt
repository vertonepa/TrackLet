package com.vertonepa.tracklet.tickets.data.local.entity.dto

import androidx.room.ColumnInfo

data class TicketListLocal(
    @ColumnInfo(name = "ticket_id") val ticketId: String,
    @ColumnInfo(name = "ticket_heading") val ticketHeading: String,
    @ColumnInfo(name = "ticket_task_progress") val ticketTaskProgress: String,
    @ColumnInfo(name = "payment_status") val paymentStatus: String?
)
