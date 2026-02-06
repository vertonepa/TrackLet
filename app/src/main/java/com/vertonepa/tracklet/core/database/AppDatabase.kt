package com.vertonepa.tracklet.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vertonepa.tracklet.tickets.data.local.dao.TicketLogsDao
import com.vertonepa.tracklet.tickets.data.local.dao.TicketsDao
import com.vertonepa.tracklet.tickets.data.local.entity.TicketLogsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity
import com.vertonepa.tracklet.timecounter.data.local.TimecounterDao
import com.vertonepa.tracklet.timecounter.data.local.TimecounterEntity


@Database(
    entities = [TicketsEntity::class, TicketLogsEntity::class, TimecounterEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TrackletConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketsDao(): TicketsDao
    abstract fun ticketLogsDao(): TicketLogsDao
    abstract fun timecounterDao(): TimecounterDao
}