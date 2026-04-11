package com.vertonepa.tracklet.tickets.domain.repository

import com.vertonepa.tracklet.tickets.domain.model.TimecounterInfo
import kotlinx.coroutines.flow.Flow

interface TimecounterRepository {
    fun getTimecounter(timecounterId: Int): Flow<TimecounterInfo>
    suspend fun changeActiveState(timecounterId: Int, isActive: Boolean)
    suspend fun saveTimeElapsed(timecounterId: Int, timeLogged: Long)
    suspend fun deleteTimecounter(timecounterId: Int)
}