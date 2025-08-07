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
        SELECT ticket_id, ticket_heading, ticket_task_progress, payment_status 
        FROM tickets_table 
        ORDER BY order_number DESC
        """
    )
    fun getTicketList(): Flow<List<TicketListLocal>>

    @Query(
        """
        SELECT ticket_id, ticket_heading, ticket_description, ticket_task_progress, payment_status 
        FROM tickets_table
        WHERE ticket_id = :id
        """
    )
    suspend fun getTicketDetails(id: String): TicketDetailsLocal

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewTicket(ticket: TicketsEntity): Long

    @Query("UPDATE tickets_table SET ticket_heading = :heading WHERE ticket_id = :id")
    suspend fun updateTicketHeading(id: String, heading: String)

    @Query("UPDATE tickets_table SET ticket_description = :description WHERE ticket_id = :id")
    suspend fun updateTicketDescription(id: String, description: String)

    @Query("UPDATE tickets_table SET ticket_task_progress = :taskProgress WHERE ticket_id = :ticketId")
    suspend fun updateTicketProgress(ticketId: String, taskProgress: String)

    @Query("UPDATE tickets_table SET payment_status = :changeStatus WHERE ticket_id = :ticketId")
    suspend fun updatePaymentStatus(ticketId: String, changeStatus: String)

    @Query("DELETE FROM tickets_table WHERE ticket_id = :id")
    suspend fun deleteTicket(id: String): Int

}