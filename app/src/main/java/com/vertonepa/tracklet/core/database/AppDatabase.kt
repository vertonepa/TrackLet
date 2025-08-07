package com.vertonepa.tracklet.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vertonepa.tracklet.tickets.data.local.dao.TicketsDao
import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity

@Database(entities = [TicketsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketsDao(): TicketsDao
}