package com.vertonepa.tracklet.tickets.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TicketWithPictures(
    @Embedded val ticket: TicketsEntity,
    @Relation(
        parentColumn = "ticket_id",
        entityColumn = "ticket_id"
    )
    val pictures: List<PictureEntity>
)
