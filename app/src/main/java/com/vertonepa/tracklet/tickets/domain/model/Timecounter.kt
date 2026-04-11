package com.vertonepa.tracklet.tickets.domain.model

import com.vertonepa.tracklet.core.datatypes.LogEntry

data class Timecounter(
    val ticketId: Int,
    val tcId: Int = 0,
    val timeLogged: Long = 0L,
    val isActive: Boolean = true,
    val logEntry: LogEntry = LogEntry.TIMER,
    val createdAt: Long = System.currentTimeMillis()
)
