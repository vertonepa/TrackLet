package com.vertonepa.tracklet.timecounter.presentation.model

import com.vertonepa.tracklet.core.datatypes.LogEntry

data class TimecounterGenerator(
    val tcId: Int,
    val ticketId: Int,
    val timeLogged: Long = 0L,
    val isActive: Boolean = false,
    val logEntry: LogEntry = LogEntry.TIMER,
    val createdAt: Long = System.currentTimeMillis()
)
