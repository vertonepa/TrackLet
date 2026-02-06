package com.vertonepa.tracklet.timecounter.data

import com.vertonepa.tracklet.timecounter.data.local.TimecounterEntity
import com.vertonepa.tracklet.timecounter.data.local.TimecounterLocal
import com.vertonepa.tracklet.timecounter.presentation.model.TimecounterInfo
import com.vertonepa.tracklet.timecounter.presentation.model.Timecounter

fun TimecounterLocal.toTimecounterInfo(): TimecounterInfo {
    return TimecounterInfo(
        timecounterId = timecounterId,
        ticketId = ticketId,
        timeLogged = timeLogged
    )
}

fun Timecounter.toEntity(): TimecounterEntity {
    return TimecounterEntity(
        timecounterId = tcId,
        ticketId = ticketId,
        timeLogged = timeLogged,
        isActive = isActive,
        logEntry = logEntry,
        createdAt = createdAt
    )
}

fun TimecounterEntity.toTimecounter(): Timecounter {
    return Timecounter(
        tcId = timecounterId,
        ticketId = ticketId,
        timeLogged = timeLogged,
        isActive = isActive,
        logEntry = logEntry,
        createdAt = createdAt
    )
}