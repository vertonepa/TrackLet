package com.vertonepa.tracklet.tickets.domain.model.enums

enum class PaymentStatus(val status: String) {
    PAID("Pagado"),
    UNNECESSARY("No Corresponde"),
    OWES("Adeuda"),
    EXEMPT("exento"),
    NOT_APPLICABLE("N/A")
}