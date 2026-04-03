package com.vertonepa.tracklet.tickets.data.repository

import com.vertonepa.tracklet.tickets.data.local.dao.TicketsDao
import com.vertonepa.tracklet.tickets.data.mappers.toPictureEntities
import com.vertonepa.tracklet.tickets.data.mappers.toTicketDetailsModel
import com.vertonepa.tracklet.tickets.data.mappers.toTicketListModel
import com.vertonepa.tracklet.tickets.data.mappers.toTicketsEntity
import com.vertonepa.tracklet.tickets.domain.model.TicketCreationModel
import com.vertonepa.tracklet.tickets.domain.model.TicketDetailsModel
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import com.vertonepa.tracklet.tickets.domain.repository.TicketsRepository
import com.vertonepa.tracklet.tickets.data.mappers.toEntity
import com.vertonepa.tracklet.tickets.domain.model.Timecounter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TicketsRepositoryImpl @Inject constructor(
    private val dao: TicketsDao
) : TicketsRepository {

    override fun getTickets(): Flow<List<TicketListModel>> {
        return dao.getTicketList()
            .map { tickets -> tickets.map { item -> item.toTicketListModel() } }
    }

    override fun getTicketDetailsById(ticketId: Int): Flow<TicketDetailsModel> {
        return dao.getTicketDetails(ticketId).map {
            it.toTicketDetailsModel()
        }
    }

    override suspend fun createNewTicket(newTicket: TicketCreationModel) {
        dao.insertNewTicketWithPictures(
            ticket = newTicket.toTicketsEntity(),
            pictures = newTicket.toPictureEntities()
        )
    }

    override suspend fun updateTicketInfo(ticketId: Int, heading: String?, description: String?) {
        heading?.let { dao.updateTicketHeading(ticketId, it) }
        description?.let { dao.updateTicketDescription(ticketId, it) }
    }

    override suspend fun updateTicketProgress(ticketId: Int, taskProgress: String) {
        /*
        se actualiza automaticamente mediante una acción del sistema
        estas acciones son:
        - Cuando se crea: TicketTaskProgressLabel.CREATED.status
        - Cuando se inicia un Timer: TicketTaskProgressLabel.IN_PROGRESS.status
        - Cuando se presiona cancelar trabajo: TicketTaskProgressLabel.CANCELLED.status
        - Cuando se termina el Timer exitosamente: TicketTaskProgressLabel.COMPLETED.status
         */
        dao.updateTicketProgress(ticketId, taskProgress)
    }

    override suspend fun deleteTicketById(ticketId: Int) {
        dao.deleteTicket(ticketId)
    }

    override fun getTicketIdFromTheActiveTimecounter(): Flow<Int> {
        return dao.getTicketIdFromTheActiveTimecounter()
    }

    override suspend fun initNewTimecounter(timecounter: Timecounter) {
        dao.insertTimecounter(timecounter.toEntity())
    }

    override fun getTimecounterId(timecounterId: Int): Flow<Int> {
        return dao.getTheActiveTimecounterId(timecounterId)
    }
}