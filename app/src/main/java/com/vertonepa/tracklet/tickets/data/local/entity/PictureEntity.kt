package com.vertonepa.tracklet.tickets.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TicketsEntity::class,
            parentColumns = ["ticket_id"],
            childColumns = ["ticket_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["ticket_id"])]
)
data class PictureEntity(
    @PrimaryKey val path: String,
    @ColumnInfo(name = "ticket_id") val ticketId: Int,
)
