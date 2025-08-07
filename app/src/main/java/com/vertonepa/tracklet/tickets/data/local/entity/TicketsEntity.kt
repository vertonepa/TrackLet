package com.vertonepa.tracklet.tickets.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets_table")
data class TicketsEntity(
    @PrimaryKey
    @ColumnInfo(name = "ticket_id") val ticketId: String,
    @ColumnInfo(name = "ticket_heading") val ticketHeading: String,
    @ColumnInfo(name = "ticket_description") val ticketDescription: String,
    @ColumnInfo(name = "ticket_publish_date") val ticketPublishDate: String,
    @ColumnInfo(name = "ticket_task_progress") val ticketTaskProgress: String,
    @ColumnInfo(name = "order_number") val orderByNumber: Long,
    @ColumnInfo(name = "payment_status") val paymentStatus: String?
)
