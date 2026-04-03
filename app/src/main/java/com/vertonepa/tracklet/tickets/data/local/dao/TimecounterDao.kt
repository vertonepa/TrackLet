package com.vertonepa.tracklet.tickets.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.vertonepa.tracklet.tickets.data.local.entity.dto.TimecounterLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface TimecounterDao {

    @Query("SELECT ticket_id, timecounter_id, time_logged FROM TimecounterEntity WHERE timecounter_id = :timecounterId")
    fun getTimecounter(timecounterId: Int): Flow<TimecounterLocal?>

    @Query("UPDATE TimecounterEntity SET is_active = :isActive WHERE timecounter_id = :timeCounterId")
    suspend fun updateIsActive(timeCounterId: Int, isActive: Boolean)

    @Query("UPDATE TimecounterEntity SET time_logged = :timeLogged WHERE timecounter_id = :timeCounterId")
    suspend fun updateTimeElapsed(timeCounterId: Int, timeLogged: Long)

    @Query("DELETE FROM TimecounterEntity WHERE timecounter_id = :timecounterId")
    suspend fun deleteTimecounter(timecounterId: Int)
}