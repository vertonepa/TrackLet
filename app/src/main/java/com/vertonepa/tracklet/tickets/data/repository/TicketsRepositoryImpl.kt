package com.vertonepa.tracklet.tickets.data.repository

import com.vertonepa.tracklet.tickets.data.local.dao.TicketsDao
import com.vertonepa.tracklet.tickets.data.mappers.toTicketDetailsModel
import com.vertonepa.tracklet.tickets.data.mappers.toTicketListModel
import com.vertonepa.tracklet.tickets.data.mappers.toTicketsEntity
import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TicketsRepositoryImpl @Inject constructor(private val dao: TicketsDao) : TicketsRepository {
    override fun getTickets(): Flow<List<TicketListModel>> {
        return dao.getTicketList()
            .map { tickets -> tickets.map { item -> item.toTicketListModel() } }
    }

    override fun getTicketDetailsById(ticketId: Int): Flow<TicketDetailsModel> {
        return dao.getTicketDetails(ticketId).map { it.toTicketDetailsModel() }
    }

    override suspend fun createNewTicket(newTicket: TicketCreationModel): Long {
        //si retorna -1L, informar a usuario por pantalla de que no se creó el ticket
        return dao.insertNewTicket(newTicket.toTicketsEntity())
    }

    override suspend fun updateTicketInfo(id: Int, heading: String?, description: String?) {
        heading?.let { dao.updateTicketHeading(id, it) }
        description?.let { dao.updateTicketDescription(id, it) }
    }

    override suspend fun updateTicketProgress(id: Int, taskProgress: String) {
        /*
        se actualiza automaticamente mediante una acción del sistema
        estas acciones son:
        - Cuando se crea: TicketTaskProgressLabel.CREATED.status
        - Cuando se inicia un Timer: TicketTaskProgressLabel.IN_PROGRESS.status
        - Cuando se presiona cancelar trabajo: TicketTaskProgressLabel.CANCELLED.status
        - Cuando se termina el Timer exitosamente: TicketTaskProgressLabel.COMPLETED.status
         */
        dao.updateTicketProgress(id, taskProgress)
    }

    override suspend fun deleteTicketById(id: Int) {
        dao.deleteTicket(id)
    }
}