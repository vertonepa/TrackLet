package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(private val repository: TicketsRepository) {
    operator fun invoke(): Flow<List<TicketListModel>> {
        return repository.getTickets().distinctUntilChanged()
    }
}