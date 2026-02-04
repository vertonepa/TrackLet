package com.vertonepa.tracklet.timecounter.data

import com.vertonepa.tracklet.timecounter.data.local.TimecounterDao
import com.vertonepa.tracklet.timecounter.presentation.model.TimecounterInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class TimecounterRepositoryImpl @Inject constructor(
    private val dao: TimecounterDao
) : TimecounterRepository {
    override fun getTimecounter(timecounterId: Int): Flow<TimecounterInfo> {
        return dao.getTimecounter(timecounterId).mapNotNull { it?.toTimecounterInfo() }
    }

    override suspend fun changeActiveState(timecounterId: Int, isActive: Boolean) {
        dao.updateIsActive(timecounterId, isActive)
    }

    override suspend fun saveTimeElapsed(timecounterId: Int, timeLogged: Long) {
        dao.updateTimeElapsed(timecounterId, timeLogged)
    }

    override suspend fun deleteTimecounter(timecounterId: Int) {
        dao.deleteTimecounter(timecounterId)
    }
}