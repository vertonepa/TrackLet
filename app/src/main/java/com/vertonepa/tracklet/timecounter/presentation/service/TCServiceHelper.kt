package com.vertonepa.tracklet.timecounter.presentation.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.vertonepa.tracklet.MainActivity
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.TC_STATE

object TCServiceHelper {
    private const val FLAG = PendingIntent.FLAG_IMMUTABLE
    private const val CLICK_CODE = 0
    private const val START_CODE = 1
    private const val PAUSE_CODE = 2
    private const val RESUME_CODE = 3
    private const val STOP_CODE = 4
    private const val CANCEL_CODE = 5

    fun clickNotification(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(TC_STATE, TimecounterValues.BACK)
        }
        return PendingIntent.getActivity(context, CLICK_CODE, intent, FLAG)
    }

    fun start(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(TC_STATE, TimecounterValues.START)
        }
        return PendingIntent.getService(context, START_CODE, intent, FLAG)
    }

    fun pause(context: Context): PendingIntent {
        val intent = Intent(context, TimecounterService::class.java).apply {
            putExtra(TC_STATE, TimecounterValues.PAUSE)
        }
        return PendingIntent.getService(context, PAUSE_CODE, intent, FLAG)
    }

    fun resume(context: Context): PendingIntent {
        val intent = Intent(context, TimecounterService::class.java).apply {
            putExtra(TC_STATE, TimecounterValues.RESUME)
        }
        return PendingIntent.getService(context, RESUME_CODE, intent, FLAG)
    }

    fun stop(context: Context): PendingIntent {
        val intent = Intent(context, TimecounterService::class.java).apply {
            putExtra(TC_STATE, TimecounterValues.STOP)
        }
        return PendingIntent.getService(context, STOP_CODE, intent, FLAG)
    }

    fun cancel(context: Context): PendingIntent {
        val intent = Intent(context, TimecounterService::class.java).apply {
            putExtra(TC_STATE, TimecounterValues.CANCEL)
        }
        return PendingIntent.getService(context, CANCEL_CODE, intent, FLAG)
    }

    fun triggerService(context: Context, action: String, ticketId: Int? = null) {
        Intent(context, TimecounterService::class.java).apply {
            this.action = action
            ticketId?.let { putExtra(TimecounterValues.TICKET_ID_EXTRA, it) }
            context.startService(this)
        }
    }
}