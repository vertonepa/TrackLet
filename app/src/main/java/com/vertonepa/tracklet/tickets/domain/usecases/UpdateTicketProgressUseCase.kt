package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import javax.inject.Inject

class UpdateTicketProgressUseCase @Inject constructor(private val repository: TicketsRepository) {
    suspend operator fun invoke(id: Int, taskProgress: String) {
        repository.updateTicketProgress(id, taskProgress)
    }
}