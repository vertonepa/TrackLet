package com.vertonepa.tracklet.timecounter.presentation.utils

abstract class TimecounterValues {
    companion object {
        const val TIMECOUNTER_STATE = "TC_STATE"

        const val START = "START"
        const val PAUSE = "PAUSE"
        const val RESUME = "RESUME"
        const val STOP = "STOP"
        const val CANCEL = "CANCEL"

        const val DEEP_LINK_TIMECOUNTER = "tracklet://tickets"

        const val NOTIFICATION_ID = 1
        const val SERVICE_NOTIFICATION_CHANNEL_ID = "Timecounter"
    }
}