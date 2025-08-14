package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketDetailsUseCase @Inject constructor(private val repository: ITicketsRepository) {
    operator fun invoke(id: String): Flow<TicketDetailsModel> {
        return repository.getTicketDetailsFromLocalById(id)
    }
}