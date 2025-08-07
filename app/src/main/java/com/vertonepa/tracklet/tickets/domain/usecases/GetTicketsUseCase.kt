package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(private val repository: ITicketsRepository) {
    operator fun invoke(): Flow<List<TicketListModel>> {
        return repository.getTicketsFromLocal().distinctUntilChanged()
    }
}