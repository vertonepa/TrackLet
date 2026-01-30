package com.vertonepa.tracklet.timecounter.data

import com.vertonepa.tracklet.timecounter.data.local.TimecounterEntity
import com.vertonepa.tracklet.timecounter.data.local.LoggedTimecounter
import com.vertonepa.tracklet.timecounter.presentation.model.Timecounter
import com.vertonepa.tracklet.timecounter.presentation.model.TimecounterGenerator

fun LoggedTimecounter.toTimecounter(): Timecounter {
    return Timecounter(
        tcId = tcId,
        ticketId = ticketId,
        timeLogged = timeLogged,
        isActive = isActive
    )
}

fun Timecounter.toTimecounterUnit(): LoggedTimecounter {
    return LoggedTimecounter(
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