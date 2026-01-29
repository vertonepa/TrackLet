package com.vertonepa.tracklet.timecounter.data

import com.vertonepa.tracklet.timecounter.data.local.TimecounterEntity
import com.vertonepa.tracklet.timecounter.data.local.TimecounterUnit
import com.vertonepa.tracklet.timecounter.presentation.Timecounter
import com.vertonepa.tracklet.timecounter.presentation.TimecounterGenerator

fun TimecounterUnit.toTimecounter(): Timecounter {
    return Timecounter(
        tcId = tcId,
        ticketId = ticketId,
        timeLogged = timeLogged,
        isActive = isActive
    )
}

fun Timecounter.toTimecounterUnit(): TimecounterUnit {
    return TimecounterUnit(
        tcId = tcId,
        ticketId = ticketId,
        timeLogged = timeLogged,
        isActive = isActive
    )
}

fun TimecounterGenerator.toEntity(): TimecounterEntity {
    return TimecounterEntity(
        tcId = tcId,
        ticketId = ticketId,
        timeLogged = timeLogged,
        isActive = isActive,
        logEntry = logEntry,
        createdAt = createdAt
    )
}