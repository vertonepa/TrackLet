package com.vertonepa.tracklet.timecounter.presentation.model

data class Timecounter(
    val tcId: Int,
    val ticketId: Int,
    val timeLogged: Long,
    val isActive: Boolean
)
