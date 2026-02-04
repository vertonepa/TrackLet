package com.vertonepa.tracklet.timecounter.presentation.model

import com.vertonepa.tracklet.core.datatypes.LogEntry

data class TimecounterInfo(
    val timecounterId: Int,
    val timeLogged: Long,
    val logEntry: LogEntry,
    val createdAt: Long
)
