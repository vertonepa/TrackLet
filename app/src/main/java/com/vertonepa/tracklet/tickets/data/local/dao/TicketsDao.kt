package com.vertonepa.tracklet.tickets.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TicketDetailsLocal
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TicketListLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketsDao {
    @Query(
        """
        SELECT ticket_id, ticket_heading, ticket_publish_date 
        FROM tickets_table 
        ORDER BY ticket_id DESC
        """
    )
    fun getTicketList(): Flow<List<TicketListLocal>>

    @Query(
        """
        SELECT ticket_id, ticket_heading, ticket_description, ticket_task_progress, payment_state, ticket_publish_date
        FROM tickets_table
        WHERE ticket_id = :id
        """
    )
    fun getTicketDetails(id: Int): Flow<TicketDetailsLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewTicket(ticket: TicketsEntity): Long

    @Query("UPDATE tickets_table SET ticket_heading = :heading WHERE ticket_id = :id")
    suspend fun updateTicketHeading(id: Int, heading: String)

    @Query("UPDATE tickets_table SET ticket_description = :description WHERE ticket_id = :id")
    suspend fun updateTicketDescription(id: Int, description: String)

    @Query("UPDATE tickets_table SET ticket_task_progress = :taskProgress WHERE ticket_id = :ticketId")
    suspend fun updateTicketProgress(ticketId: Int, taskProgress: String)

    @Query("UPDATE tickets_table SET payment_state = :changeState WHERE ticket_id = :ticketId")
    suspend fun updatePaymentStatus(ticketId: Int, changeState: String)

    @Query("DELETE FROM tickets_table WHERE ticket_id = :id")
    suspend fun deleteTicket(id: Int): Int

}