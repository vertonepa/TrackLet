package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import javax.inject.Inject

class UpdateTicketProgressUseCase @Inject constructor(private val repository: ITicketsRepository) {
    suspend operator fun invoke(id: String, taskProgress: String) {
        repository.updateTicketProgress(id, taskProgress)
    }
}