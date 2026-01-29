package com.vertonepa.tracklet.core.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.vertonepa.tracklet.core.datatypes.LogEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ProvidedTypeConverter
class TrackletConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(date: String): LocalDate {
        return LocalDate.parse(date, formatter)
    }

    @TypeConverter
    fun toInt(logEntry: LogEntry): Int {
        return logEntry.ordinal
    }

    @TypeConverter
    fun toLogEntry(ordinal: Int): LogEntry {
        return LogEntry.entries[ordinal]
    }
}