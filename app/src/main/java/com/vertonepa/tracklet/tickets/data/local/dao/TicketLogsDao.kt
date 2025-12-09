package com.vertonepa.tracklet.tickets.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vertonepa.tracklet.tickets.data.local.entity.TicketLogsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TotalsLocal
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TicketLogsDao {

    @Query(
        """
        SELECT * 
        FROM TicketLogsEntity 
        WHERE ticket_id = :id
        ORDER BY log_id DESC
        """
    )
    fun getTicketLogsById(id: Int): Flow<List<TicketLogsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicketLog(ticketLog: TicketLogsEntity)

    @Query(
        """
        SELECT COALESCE(SUM(quantity), 0) AS quantity
        FROM TicketLogsEntity
        WHERE ticket_id = :id
        """
    )
    fun getTotals(id: Int): Flow<TotalsLocal>

    @Query("DELETE FROM TicketLogsEntity WHERE log_id IN (:ids)")
    suspend fun deleteLogFromLocal(ids: Set<Int>)
}