package com.vertonepa.tracklet.tickets.data.repository

import android.util.Log
import com.vertonepa.tracklet.tickets.data.local.dao.TicketLogsDao
import com.vertonepa.tracklet.tickets.data.mappers.toDomain
import com.vertonepa.tracklet.tickets.data.mappers.toEntity
import com.vertonepa.tracklet.tickets.domain.model.TicketLog
import com.vertonepa.tracklet.tickets.domain.model.Totals
import com.vertonepa.tracklet.tickets.domain.repository.TicketLogsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TicketLogsRepositoryImpl @Inject constructor(
    private val ticketLogsDao: TicketLogsDao
) : TicketLogsRepository {
    override suspend fun generateLog(ticketLog: TicketLog) {
        ticketLogsDao.insertTicketLog(ticketLog.toEntity())
    }

    override fun getLogs(id: Int): Flow<List<TicketLog>> {
        return ticketLogsDao.getTicketLogsById(id)
            .map { ticketLogs -> ticketLogs.map { it.toDomain() } }
    }

    override fun getTotals(id: Int): Flow<Totals> {
        Log.d("TOTALS", "revisar id del Totals si falla")
        return ticketLogsDao.getTotals(id).map { it.toDomain() }
    }

    override suspend fun logDelete(ids: Set<Int>) {
        ticketLogsDao.deleteLogFromLocal(ids)
    }
}