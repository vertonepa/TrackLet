package com.vertonepa.tracklet.tickets.domain.model.enums

enum class PaymentStatus(val status: String) {
    PAID("Pagado"),
    UNNECESSARY("No Corresponde"),
    IN_DEBT("Debe"),
}