package com.vertonepa.tracklet.timecounter.presentation

data class Timecounter(
    val tcId: Int,
    val ticketId: Int,
    val timeLogged: Long,
    val isActive: Boolean
)
