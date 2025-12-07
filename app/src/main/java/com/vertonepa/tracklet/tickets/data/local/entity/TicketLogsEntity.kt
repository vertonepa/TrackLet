package com.vertonepa.tracklet.tickets.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TicketsEntity::class,
            parentColumns = ["ticket_id"],
            childColumns = ["ticket_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TicketLogsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "log_id") val logId: Int = 0,
    @ColumnInfo(name = "ticket_id") val ticketId: UUID,
    @ColumnInfo(name = "payment_state") val paymentState: String,
    val date: LocalDate,
    val quantity: Int,
    val color: String,
    @ColumnInfo(name = "has_mark") val hasMark: Boolean
)
