package com.vertonepa.tracklet.tickets.data.mappers

import com.vertonepa.tracklet.tickets.data.local.entity.PictureEntity
import com.vertonepa.tracklet.tickets.data.local.entity.TicketLogsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.TicketWithPictures
import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity
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
        heading = this.heading,
        description = this.description,
        publishDate = this.publishDate,
        paymentState = this.paymentState,
        taskProgress = this.taskProgress
    )
}

fun TicketCreationModel.toPictureEntities(): List<PictureEntity> {
    return this.pictures.map { path ->
        PictureEntity(
            path = path,
            ticketId = this.ticketId
        )
    }
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
        ticketHeading = this.heading,
        ticketPublishDate = this.publishDate,
    )
}

fun TicketWithPictures.toTicketDetailsModel(): TicketDetailsModel {
    val ticket = this.ticket

    return TicketDetailsModel(
        ticketId = ticket.ticketId,
        ticketHeading = ticket.heading,
        ticketDescription = ticket.description,
        paymentState = ticket.paymentState,
        ticketTaskProgress = ticket.taskProgress,
        ticketPublishDate = ticket.publishDate,
        pictures = this.pictures.map { it.path }
    )
}