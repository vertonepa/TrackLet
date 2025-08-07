package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import javax.inject.Inject

class UpdateTicketInfoUseCase @Inject constructor(private val repository: ITicketsRepository) {
    suspend operator fun invoke(id: String, heading: String? = null, description: String? = null) {
        repository.updateTicketInfo(id, heading, description)
    }
}