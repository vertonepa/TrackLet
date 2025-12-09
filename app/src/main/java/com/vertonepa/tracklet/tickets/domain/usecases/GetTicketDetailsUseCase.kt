package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketDetailsUseCase @Inject constructor(private val repository: TicketsRepository) {
    operator fun invoke(id: Int): Flow<TicketDetailsModel> {
        return repository.getTicketDetailsById(id)
    }
}