package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import javax.inject.Inject

class GetTicketDetailsUseCase @Inject constructor(private val repository: ITicketsRepository) {
    suspend operator fun invoke(id: String): TicketDetailsModel {
        return repository.getTicketDetailsFromLocalById(id)
    }
}