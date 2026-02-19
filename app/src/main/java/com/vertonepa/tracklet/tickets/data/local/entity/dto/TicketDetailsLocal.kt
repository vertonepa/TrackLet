package com.vertonepa.tracklet.tickets.data.local.entity.dto

import androidx.room.ColumnInfo
import java.time.LocalDate

data class TicketDetailsLocal(
    @ColumnInfo(name = "id") val ticketId: Int,
    @ColumnInfo(name = "heading") val heading: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publish_date") val publishDate: LocalDate,
    @ColumnInfo(name = "task_progress") val taskProgress: String,
    @ColumnInfo(name = "payment_state") val paymentState: String
)

