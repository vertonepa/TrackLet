package com.vertonepa.tracklet.tickets.data.local.entity.dto

import androidx.room.ColumnInfo
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import java.time.LocalDate

data class TicketListLocal(
    @ColumnInfo(name = "ticket_id") val ticketId: String,
    @ColumnInfo(name = "ticket_heading") val ticketHeading: String,
    @ColumnInfo(name = "ticket_publish_date") val ticketPublishDate: LocalDate,
)

fun TicketListLocal.toTicketListModel(): TicketListModel {
    return TicketListModel(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketPublishDate = this.ticketPublishDate,
    )
}