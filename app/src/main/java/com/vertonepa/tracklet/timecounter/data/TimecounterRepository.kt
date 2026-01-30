package com.vertonepa.tracklet.timecounter.data

import com.vertonepa.tracklet.timecounter.presentation.model.Timecounter
import com.vertonepa.tracklet.timecounter.presentation.model.TimecounterGenerator
import kotlinx.coroutines.flow.Flow

interface TimecounterRepository {
    fun getTimecounter(tcId: Int): Flow<Timecounter>
    suspend fun activeState(tcId: Int, isActive: Boolean)
    suspend fun changeTimeLogged(tcId: Int, timeLogged: Long)
    suspend fun createNewTimecounter(timecounter: TimecounterGenerator)
    suspend fun deleteTimecounter(tcId: Int, ticketId: Int)
}