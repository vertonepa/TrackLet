package com.vertonepa.tracklet.timecounter.presentation

import com.vertonepa.tracklet.core.datatypes.LogEntry

data class TimecounterGenerator(
    val tcId: Int,
    val ticketId: Int,
    val timeLogged: Long,
    val isActive: Boolean,
    val logEntry: LogEntry = LogEntry.TIMER,
    val createdAt: Long = System.currentTimeMillis()
)
