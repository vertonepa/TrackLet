package com.vertonepa.tracklet.timecounter.data

import com.vertonepa.tracklet.timecounter.data.local.TimecounterDao
import com.vertonepa.tracklet.timecounter.presentation.Timecounter
import com.vertonepa.tracklet.timecounter.presentation.TimecounterGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TimecounterRepositoryImpl @Inject constructor(
    private val dao: TimecounterDao
) : TimecounterRepository {
    override fun getTimecounter(tcId: Int): Flow<Timecounter> {
        return dao.getTimecounter(tcId).map { it.toTimecounter() }
    }

    override suspend fun activeState(tcId: Int, isActive: Boolean) {
        dao.updateIsActive(tcId, isActive)
    }

    override suspend fun changeTimeLogged(tcId: Int, timeLogged: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun createNewTimecounter(timecounter: TimecounterGenerator) {
        dao.insertTimecounter(timecounter.toEntity())
    }

    override suspend fun deleteTimecounter(tcId: Int, ticketId: Int) {
        dao.deleteTimecounter(tcId, ticketId)
    }
}