package com.vertonepa.tracklet.tickets.presentation.ticketlogs

import com.vertonepa.tracklet.tickets.domain.model.Totals

fun Totals.toUi(): UiTotals {
    return UiTotals(quantity = this.quantity)
}