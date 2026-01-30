package com.vertonepa.tracklet.timecounter.data.local

import androidx.room.ColumnInfo

data class LoggedTimecounter(
    @ColumnInfo(name = "tc_id") val tcId: Int,
    @ColumnInfo(name = "ticket_id") val ticketId: Int,
    @ColumnInfo(name = "time_logged") val timeLogged: Long,
    @ColumnInfo(name = "is_active") val isActive: Boolean
)
