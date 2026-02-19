package com.vertonepa.tracklet.tickets.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class TicketsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ticket_id") val ticketId: Int = 0,
    @ColumnInfo(name = "heading") val heading: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publish_date") val publishDate: LocalDate,
    @ColumnInfo(name = "task_progress") val taskProgress: String,
    @ColumnInfo(name = "payment_state") val paymentState: String
)
