package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import javax.inject.Inject

class DeleteTicketByIdUseCase @Inject constructor(private val repository: TicketsRepository) {
    suspend operator fun invoke(ticketId: Int) {
        repository.deleteTicketById(ticketId)
    }
}