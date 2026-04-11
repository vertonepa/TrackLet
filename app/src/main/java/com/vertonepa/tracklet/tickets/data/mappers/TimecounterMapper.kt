package com.vertonepa.tracklet.tickets.data.mappers

import com.vertonepa.tracklet.tickets.data.local.entity.TimecounterEntity
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TimecounterLocal
import com.vertonepa.tracklet.tickets.domain.model.TimecounterInfo
import com.vertonepa.tracklet.tickets.domain.model.Timecounter

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