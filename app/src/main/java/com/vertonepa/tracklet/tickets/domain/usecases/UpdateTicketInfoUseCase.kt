package com.vertonepa.tracklet.tickets.domain.usecases

import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import javax.inject.Inject

class UpdateTicketInfoUseCase @Inject constructor(private val repository: TicketsRepository) {
    suspend operator fun invoke(id: Int, heading: String? = null, description: String? = null) {
        repository.updateTicketInfo(id, heading, description)
    }
}