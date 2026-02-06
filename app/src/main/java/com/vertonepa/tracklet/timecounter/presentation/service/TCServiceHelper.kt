package com.vertonepa.tracklet.timecounter.presentation.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.vertonepa.tracklet.MainActivity
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.DEEP_LINK_TIMECOUNTER
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.TIMECOUNTER_STATE

object TCServiceHelper {
    private const val FLAG = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    private const val CLICK_CODE = 0
    private const val PAUSE_CODE = 1
    private const val RESUME_CODE = 2
    private const val STOP_CODE = 3

    fun clickNotification(context: Context, timecounterId: Int): PendingIntent {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "$DEEP_LINK_TIMECOUNTER/$timecounterId?shouldShowDialog=false".toUri(),
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
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

    fun stop(context: Context, timecounterId: Int): PendingIntent {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "$DEEP_LINK_TIMECOUNTER/$timecounterId?shouldShowDialog=true".toUri(),
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        return PendingIntent.getActivity(context, STOP_CODE, intent, FLAG)
    }

    fun triggerService(context: Context, action: String) {
        Intent(context, TimecounterService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }
}