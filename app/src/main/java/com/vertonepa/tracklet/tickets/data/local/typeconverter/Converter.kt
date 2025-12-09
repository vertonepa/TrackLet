package com.vertonepa.tracklet.tickets.data.local.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ProvidedTypeConverter
class Converter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(date: String): LocalDate {
        return LocalDate.parse(date, formatter)
    }
}