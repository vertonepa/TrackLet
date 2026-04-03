package com.vertonepa.tracklet.tickets.presentation.timecounter

import com.vertonepa.tracklet.tickets.domain.model.TimecounterInfo

sealed class TimecounterUiState {

    data object Loading : TimecounterUiState()

    data class Success(val timecounter: TimecounterInfo) : TimecounterUiState()
}