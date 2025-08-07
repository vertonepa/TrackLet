package com.vertonepa.tracklet.tickets.domain.repository

import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import kotlinx.coroutines.flow.Flow

interface ITicketsRepository {
    fun getTicketsFromLocal(): Flow<List<TicketListModel>>

    suspend fun getTicketDetailsFromLocalById(ticketId: String): TicketDetailsModel

    suspend fun createNewTicket(newTicket: TicketCreationModel): Long

    suspend fun updateTicketInfo(id: String, heading: String?, description: String?)

    suspend fun updateTicketProgress(id: String, taskProgress: String)

    suspend fun deleteTicketById(id: String): Int
}