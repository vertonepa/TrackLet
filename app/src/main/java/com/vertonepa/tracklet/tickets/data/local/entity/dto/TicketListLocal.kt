package com.vertonepa.tracklet.tickets.data.local.entity.dto

import androidx.room.ColumnInfo
import java.time.LocalDate

data class TicketListLocal(
    @ColumnInfo(name = "ticket_id") val ticketId: Int,
    @ColumnInfo(name = "heading") val heading: String,
    @ColumnInfo(name = "publish_date") val publishDate: LocalDate,
)

