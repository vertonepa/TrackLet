package com.vertonepa.tracklet.timecounter.data

import com.vertonepa.tracklet.timecounter.presentation.model.TimecounterInfo
import kotlinx.coroutines.flow.Flow

interface TimecounterRepository {
    fun getTimecounter(timecounterId: Int): Flow<TimecounterInfo>
    suspend fun changeActiveState(timecounterId: Int, isActive: Boolean)
    suspend fun saveTimeElapsed(timecounterId: Int, timeLogged: Long)
    suspend fun deleteTimecounter(timecounterId: Int)
}