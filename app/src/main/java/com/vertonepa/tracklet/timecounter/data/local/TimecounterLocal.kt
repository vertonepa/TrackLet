package com.vertonepa.tracklet.timecounter.data.local

import androidx.room.ColumnInfo
import com.vertonepa.tracklet.core.datatypes.LogEntry

data class TimecounterLocal(
    @ColumnInfo(name = "timecounter_id") val timecounterId: Int,
    @ColumnInfo(name = "time_logged") val timeLogged: Long,
    @ColumnInfo(name = "log_entry") val logEntry: LogEntry,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
