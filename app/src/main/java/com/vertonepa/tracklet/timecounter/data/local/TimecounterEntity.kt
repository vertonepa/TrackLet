package com.vertonepa.tracklet.timecounter.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vertonepa.tracklet.core.datatypes.LogEntry
import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TicketsEntity::class,
            parentColumns = ["ticket_id"],
            childColumns = ["ticket_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["ticket_id"])]
)
data class TimecounterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timecounter_id") val timecounterId: Int = 0,
    @ColumnInfo(name = "ticket_id") val ticketId: Int,
    @ColumnInfo(name = "time_logged") val timeLogged: Long,
    @ColumnInfo(name = "is_active") val isActive: Boolean,
    @ColumnInfo(name = "log_entry") val logEntry: LogEntry,
    @ColumnInfo(name = "created_at") val createdAt: Long
)