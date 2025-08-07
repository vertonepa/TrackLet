package com.vertonepa.tracklet.tickets.data.mapper

import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TicketDetailsLocal
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TicketListLocal
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel

fun TicketCreationModel.toEntity(): TicketsEntity {
    return TicketsEntity(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketDescription = this.ticketDescription,
        ticketPublishDate = this.ticketPublishDate.toString(),
        ticketTaskProgress = this.ticketTaskProgress,
        orderByNumber = this.orderByNumber,
        paymentStatus = this.paymentStatus
    )
}

fun TicketDetailsLocal.toTicketDetailsModel(): TicketDetailsModel {
    return TicketDetailsModel(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketDescription = this.ticketDescription,
        paymentStatus = this.paymentStatus,
        ticketTaskProgress = this.ticketTaskProgress,
    )
}

fun TicketListLocal.toTicketListModel(): TicketListModel {
    return TicketListModel(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketTaskProgress = this.ticketTaskProgress,
    )
}

