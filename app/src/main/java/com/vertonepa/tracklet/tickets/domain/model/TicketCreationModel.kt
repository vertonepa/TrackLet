package com.vertonepa.tracklet.tickets.domain.model

import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity
import com.vertonepa.tracklet.tickets.domain.model.enums.PaymentStatus
import com.vertonepa.tracklet.tickets.domain.model.enums.TicketTaskProgressLabel
import java.time.LocalDate
import java.util.UUID

data class TicketCreationModel(
    val ticketId: String = UUID.randomUUID().toString(),
    val ticketHeading: String,
    val ticketDescription: String,
    val ticketPublishDate: LocalDate = LocalDate.now(),
    val paymentStatus: String = PaymentStatus.UNNECESSARY.status,
    val orderByNumber: Long = System.currentTimeMillis(),
    val ticketTaskProgress: String = TicketTaskProgressLabel.CREATED.status,
)

fun TicketCreationModel.toTicketsEntity(): TicketsEntity {
    return TicketsEntity(
        ticketId = this.ticketId,
        ticketHeading = this.ticketHeading,
        ticketDescription = this.ticketDescription,
        ticketPublishDate = this.ticketPublishDate,
        ticketTaskProgress = this.ticketTaskProgress,
        orderByNumber = this.orderByNumber,
        paymentStatus = this.paymentStatus
    )
}