package com.vertonepa.tracklet.tickets.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vertonepa.tracklet.tickets.data.local.entity.PictureEntity
import com.vertonepa.tracklet.tickets.data.local.entity.TicketWithPictures
import com.vertonepa.tracklet.tickets.data.local.entity.TicketsEntity
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TicketListLocal
import com.vertonepa.tracklet.tickets.data.local.entity.TimecounterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketsDao {
    @Query(
        """
        SELECT ticket_id, heading, publish_date 
        FROM TicketsEntity 
        ORDER BY ticket_id DESC
        """
    )
    fun getTicketList(): Flow<List<TicketListLocal>>

    @Transaction
    @Query("SELECT * FROM TicketsEntity WHERE ticket_id = :id")
    fun getTicketDetails(id: Int): Flow<TicketWithPictures>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTicket(ticket: TicketsEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictures(pictures: List<PictureEntity>)

    @Transaction
    @Insert
    suspend fun insertNewTicketWithPictures(ticket: TicketsEntity, pictures: List<PictureEntity>) {
        val ticketId = insertNewTicket(ticket)
        val picturesWithId = pictures.map { it.copy(ticketId = ticketId.toInt()) }
        insertPictures(picturesWithId)
    }

    @Query("UPDATE TicketsEntity SET heading = :heading WHERE ticket_id = :id")
    suspend fun updateTicketHeading(id: Int, heading: String)

    @Query("UPDATE TicketsEntity SET description = :description WHERE ticket_id = :id")
    suspend fun updateTicketDescription(id: Int, description: String)

    @Query("UPDATE TicketsEntity SET task_progress = :taskProgress WHERE ticket_id = :ticketId")
    suspend fun updateTicketProgress(ticketId: Int, taskProgress: String)

    @Query("UPDATE TicketsEntity SET payment_state = :changeState WHERE ticket_id = :ticketId")
    suspend fun updatePaymentStatus(ticketId: Int, changeState: String)

    @Query("DELETE FROM TicketsEntity WHERE ticket_id = :id")
    suspend fun deleteTicket(id: Int): Int

    @Query("SELECT ticket_id FROM TimecounterEntity WHERE is_active = 1 LIMIT 1")
    fun getTicketIdFromTheActiveTimecounter(): Flow<Int>

    @Query("SELECT timecounter_id FROM TimecounterEntity WHERE ticket_id = :ticketId AND is_active = 1")
    fun getTheActiveTimecounterId(ticketId: Int): Flow<Int>

    @Insert
    suspend fun insertTimecounter(timecounter: TimecounterEntity)
}