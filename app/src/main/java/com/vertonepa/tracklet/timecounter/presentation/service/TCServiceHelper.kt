package com.vertonepa.tracklet.timecounter.presentation.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.vertonepa.tracklet.MainActivity
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.TIMECOUNTER_STATE

object TCServiceHelper {
    private const val FLAG = PendingIntent.FLAG_IMMUTABLE
    private const val CLICK_CODE = 0
    private const val PAUSE_CODE = 1
    private const val RESUME_CODE = 2
    private const val STOP_CODE = 3

    fun clickNotification(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(TIMECOUNTER_STATE, TimecounterValues.BACK)
        }
        return PendingIntent.getActivity(context, CLICK_CODE, intent, FLAG)
    }

    fun pause(context: Context): PendingIntent {
        val intent = Intent(context, TimecounterService::class.java).apply {
            putExtra(TIMECOUNTER_STATE, TimecounterValues.PAUSE)
        }
        return PendingIntent.getService(context, PAUSE_CODE, intent, FLAG)
    }

    fun resume(context: Context): PendingIntent {
        val intent = Intent(context, TimecounterService::class.java).apply {
            putExtra(TIMECOUNTER_STATE, TimecounterValues.RESUME)
        }
        return PendingIntent.getService(context, RESUME_CODE, intent, FLAG)
    }

    fun stop(context: Context): PendingIntent {
        val intent = Intent(context, TimecounterService::class.java).apply {
            putExtra(TIMECOUNTER_STATE, TimecounterValues.STOP)
        }
        return PendingIntent.getService(context, STOP_CODE, intent, FLAG)
    }

    fun triggerService(context: Context, action: String) {
        Intent(context, TimecounterService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }
}