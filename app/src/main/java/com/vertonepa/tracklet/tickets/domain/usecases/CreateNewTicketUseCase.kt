package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import javax.inject.Inject

class CreateNewTicketUseCase @Inject constructor(private val repository: ITicketsRepository) {
    suspend operator fun invoke(newTicket: TicketCreationModel): Long {
        return repository.createNewTicket(newTicket)
    }
}