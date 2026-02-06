package com.vertonepa.tracklet.timecounter.presentation.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.vertonepa.tracklet.MainActivity
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.TIMECOUNTER_STATE

object TCServiceHelper {
    private const val FLAG = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    private const val CLICK_CODE = 0
    private const val PAUSE_CODE = 1
    private const val RESUME_CODE = 2
    private const val STOP_CODE = 3

    private fun createIntentWithRoute(route: String, context: Context): Intent = Intent(
        Intent.ACTION_VIEW,
        route.toUri(),
        context,
        MainActivity::class.java
    )

    fun clickNotification(context: Context, ticketId: Int, timecounterId: Int): PendingIntent {
        val listIntent = createIntentWithRoute("tracklet://tickets", context)
        val detailsIntent = createIntentWithRoute("tracklet://tickets/details/$ticketId", context)
        val timecounterIntent = createIntentWithRoute(
            "tracklet://tickets/timecounter/$ticketId/$timecounterId?shouldShowDialog=false",
            context
        )

        return TaskStackBuilder.create(context)
            .addNextIntent(listIntent)
            .addNextIntent(detailsIntent)
            .addNextIntent(timecounterIntent)
            .getPendingIntent(CLICK_CODE, FLAG)!!
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

    fun stop(context: Context, ticketId: Int, timecounterId: Int): PendingIntent {
        val listIntent = createIntentWithRoute("tracklet://tickets", context)
        val detailsIntent = createIntentWithRoute("tracklet://tickets/details/$ticketId", context)
        val timecounterIntent = createIntentWithRoute(
            "tracklet://tickets/timecounter/$ticketId/$timecounterId?shouldShowDialog=false",
            context
        )

        return TaskStackBuilder.create(context)
            .addNextIntent(listIntent)
            .addNextIntent(detailsIntent)
            .addNextIntent(timecounterIntent)
            .getPendingIntent(STOP_CODE, FLAG)!!
    }

    fun triggerService(context: Context, action: String) {
        Intent(context, TimecounterService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }
}