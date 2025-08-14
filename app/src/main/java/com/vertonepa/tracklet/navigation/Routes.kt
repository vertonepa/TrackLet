package com.vertonepa.tracklet.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Tickets

@Serializable
object TicketCreation

@Serializable
object Notifications

@Serializable
object Settings

@Serializable
data class Details(val id: String)

@Serializable
data class Editing(val id: String)