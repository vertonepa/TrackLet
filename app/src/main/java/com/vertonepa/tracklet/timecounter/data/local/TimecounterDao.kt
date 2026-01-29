package com.vertonepa.tracklet.timecounter.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimecounterDao {

    @Query("SELECT tc_id, ticket_id, time_logged, is_active FROM TimecounterEntity WHERE tc_id = :tcId")
    fun getTimecounter(tcId: Int): Flow<TimecounterUnit>

    @Query("UPDATE TimecounterEntity SET is_active = :isActive WHERE tc_id = :tcId")
    suspend fun updateIsActive(tcId: Int, isActive: Boolean)

    @Query("UPDATE TimecounterEntity SET time_logged = :timeLogged WHERE tc_id = :tcId")
    suspend fun updateTimeLogged(tcId: Int, timeLogged: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimecounter(timecounter: TimecounterEntity)

    @Query("DELETE FROM TimecounterEntity WHERE tc_id = :tcId AND ticket_id = :ticketId")
    suspend fun deleteTimecounter(tcId: Int, ticketId: Int)
}