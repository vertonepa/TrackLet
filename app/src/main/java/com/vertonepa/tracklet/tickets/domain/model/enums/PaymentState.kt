package com.vertonepa.tracklet.tickets.domain.model.enums

enum class PaymentState(val state: String) {
    PAID("Pagado"),
    OWES("Adeuda"),
    EXEMPT("exento"),
    NOT_APPLICABLE("N/A")
}