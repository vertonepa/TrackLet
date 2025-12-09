package com.vertonepa.tracklet.tickets.domain.model.enums

enum class TicketTaskProgressLabel(val state: String) {
    CREATED("Creado"),
    IN_PROGRESS("Avanzando"),
    CANCELLED("Cancelado"),
    COMPLETED("Completado"),
}