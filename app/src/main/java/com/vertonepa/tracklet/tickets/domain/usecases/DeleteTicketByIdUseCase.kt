package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import javax.inject.Inject

class DeleteTicketByIdUseCase @Inject constructor(private val repository: ITicketsRepository) {
    suspend operator fun invoke(ticket: TicketDetailsModel): Int {
        return repository.deleteTicketById(ticket.ticketId)
    }

    suspend operator fun invoke(ticketId: String): Int {
        return repository.deleteTicketById(ticketId)
    }
}