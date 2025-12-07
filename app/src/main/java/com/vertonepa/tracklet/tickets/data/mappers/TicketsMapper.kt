package com.vertonepa.tracklet.tickets.data.mappers

import com.vertonepa.tracklet.tickets.data.local.entity.TicketLogsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TotalsLocal
import com.vertonepa.tracklet.tickets.domain.model.TicketLog
import com.vertonepa.tracklet.tickets.domain.model.Totals

fun TicketLog.toEntity(): TicketLogsEntity {
    return TicketLogsEntity(
        ticketId = this.ticketId,
        paymentState = this.paymentState,
        date = this.date,
        quantity = this.quantity,
        color = this.color,
        hasMark = this.hasMark,
    )
}

fun TicketLogsEntity.toDomain(): TicketLog {
    return TicketLog(
        logId = this.logId,
        ticketId = this.ticketId,
        paymentState = this.paymentState,
        date = this.date,
        quantity = this.quantity,
        color = this.color,
        hasMark = this.hasMark,
    )
}

fun TotalsLocal.toDomain(): Totals {
    return Totals(quantity = this.quantity)
}