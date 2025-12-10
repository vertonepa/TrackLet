package com.vertonepa.tracklet.tickets.domain.repository

import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import kotlinx.coroutines.flow.Flow

interface TicketsRepository {
    fun getTickets(): Flow<List<TicketListModel>>

    fun getTicketDetailsById(ticketId: Int): Flow<TicketDetailsModel>

    suspend fun createNewTicket(newTicket: TicketCreationModel): Long

    suspend fun updateTicketInfo(id: Int, heading: String?, description: String?)

    suspend fun updateTicketProgress(id: Int, taskProgress: String)

    suspend fun deleteTicketById(id: Int)
}