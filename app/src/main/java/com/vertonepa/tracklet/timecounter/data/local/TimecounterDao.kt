package com.vertonepa.tracklet.timecounter.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimecounterDao {

    @Query("SELECT timecounter_id, time_logged, log_entry, created_at FROM TimecounterEntity WHERE timecounter_id = :timecounterId")
    fun getTimecounter(timecounterId: Int): Flow<TimecounterLocal?>

    @Query("UPDATE TimecounterEntity SET is_active = :isActive WHERE timecounter_id = :timeCounterId")
    suspend fun updateIsActive(timeCounterId: Int, isActive: Boolean)

    @Query("UPDATE TimecounterEntity SET time_logged = :timeLogged WHERE timecounter_id = :timeCounterId")
    suspend fun updateTimeElapsed(timeCounterId: Int, timeLogged: Long)

    @Query("DELETE FROM TimecounterEntity WHERE timecounter_id = :timecounterId")
    suspend fun deleteTimecounter(timecounterId: Int)
}