package com.vertonepa.tracklet.tickets.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tickets_table")
data class TicketsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ticket_id") val ticketId: Int,
    @ColumnInfo(name = "ticket_heading") val ticketHeading: String,
    @ColumnInfo(name = "ticket_description") val ticketDescription: String,
    @ColumnInfo(name = "ticket_publish_date") val ticketPublishDate: LocalDate,
    @ColumnInfo(name = "ticket_task_progress") val ticketTaskProgress: String,
    @ColumnInfo(name = "payment_state") val paymentState: String
)
