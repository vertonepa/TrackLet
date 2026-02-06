package com.vertonepa.tracklet.timecounter.presentation.utils

import java.util.Locale

fun Time.totalInSeconds(): Long {
    return this.hours * 3600L + this.minutes * 60L + this.seconds
}

fun Long.toTime(): Time {
    val hours = (this / 3600).toInt()
    val minutes = (this % 3600 / 60).toInt()
    val seconds = (this % 60).toInt()

    return Time(hours, minutes, seconds)
}

fun Time.formatToString(): String {
    return String.format(
        Locale.US,
        "%02d:%02d:%02d",
        this.hours,
        this.minutes,
        this.seconds
    )
}