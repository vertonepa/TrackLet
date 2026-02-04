package com.vertonepa.tracklet.timecounter.presentation

import com.vertonepa.tracklet.timecounter.presentation.model.TimecounterInfo

sealed class TimecounterUiState {

    data object Loading : TimecounterUiState()

    data class Success(val timecounter: TimecounterInfo) : TimecounterUiState()
}