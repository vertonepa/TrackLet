package com.vertonepa.tracklet.tickets.data.mappers

import com.vertonepa.tracklet.tickets.data.local.entity.TicketLogsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TicketDetailsLocal
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TicketListLocal
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TotalsLocal
import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.model.TicketLog
import com.vertonepa.tracklet.tickets.domain.model.Totals

fun TicketCreationModel.toTicketsEntity(): TicketsEntity {
    return TicketsEntity(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketDescription = this.ticketDescription,
        ticketPublishDate = this.ticketPublishDate,
        paymentState = this.paymentState,
        ticketTaskProgress = this.ticketTaskProgress
    )
}

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

fun TicketListLocal.toTicketListModel(): TicketListModel {
    return TicketListModel(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketPublishDate = this.ticketPublishDate,
    )
}

fun TicketDetailsLocal.toTicketDetailsModel(): TicketDetailsModel {
    return TicketDetailsModel(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketDescription = this.ticketDescription,
        paymentState = this.paymentState,
        ticketTaskProgress = this.ticketTaskProgress,
        ticketPublishDate = this.ticketPublishDate
    )
}