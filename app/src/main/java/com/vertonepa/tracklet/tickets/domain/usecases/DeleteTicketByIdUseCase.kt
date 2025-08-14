package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import javax.inject.Inject

class DeleteTicketByIdUseCase @Inject constructor(private val repository: ITicketsRepository) {
    suspend operator fun invoke(ticketId: String) {
        repository.deleteTicketById(ticketId)
    }
}