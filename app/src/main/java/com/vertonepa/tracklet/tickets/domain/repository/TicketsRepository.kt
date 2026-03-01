package com.vertonepa.tracklet.tickets.domain.repository

import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.timecounter.presentation.model.Timecounter
import kotlinx.coroutines.flow.Flow

interface TicketsRepository {
    fun getTickets(): Flow<List<TicketListModel>>

    fun getTicketDetailsById(ticketId: Int): Flow<TicketDetailsModel>

    suspend fun createNewTicket(newTicket: TicketCreationModel)

    suspend fun updateTicketInfo(ticketId: Int, heading: String?, description: String?)

    suspend fun updateTicketProgress(ticketId: Int, taskProgress: String)

    suspend fun deleteTicketById(ticketId: Int)

    fun getTicketIdFromTheActiveTimecounter(): Flow<Int>

    suspend fun initNewTimecounter(timecounter: Timecounter)

    fun getTimecounterId(timecounterId: Int): Flow<Int>
}