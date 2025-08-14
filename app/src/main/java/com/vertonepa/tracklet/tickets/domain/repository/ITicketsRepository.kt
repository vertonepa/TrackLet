package com.vertonepa.tracklet.tickets.domain.repository

import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import kotlinx.coroutines.flow.Flow

interface ITicketsRepository {
    fun getTicketsFromLocal(): Flow<List<TicketListModel>>

    fun getTicketDetailsFromLocalById(ticketId: String): Flow<TicketDetailsModel>

    suspend fun createNewTicket(newTicket: TicketCreationModel): Long

    suspend fun updateTicketInfo(id: String, heading: String?, description: String?)

    suspend fun updateTicketProgress(id: String, taskProgress: String)

    suspend fun deleteTicketById(id: String)
}