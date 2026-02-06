package com.vertonepa.tracklet.timecounter.data.local

import androidx.room.ColumnInfo

data class TimecounterLocal(
    @ColumnInfo(name = "timecounter_id") val timecounterId: Int,
    @ColumnInfo(name = "ticket_id") val ticketId: Int,
    @ColumnInfo(name = "time_logged") val timeLogged: Long
)
