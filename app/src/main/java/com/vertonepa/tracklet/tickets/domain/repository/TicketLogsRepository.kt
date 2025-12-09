package com.vertonepa.tracklet.tickets.domain.repository

import com.vertonepa.tracklet.tickets.domain.model.TicketLog
import com.vertonepa.tracklet.tickets.domain.model.Totals
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TicketLogsRepository {
    suspend fun generateLog(ticketLog: TicketLog)
    fun getLogs(id: Int): Flow<List<TicketLog>>
    fun getTotals(id: Int): Flow<Totals>
    suspend fun logDelete(ids: Set<Int>)
}