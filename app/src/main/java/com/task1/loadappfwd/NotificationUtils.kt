package com.task1.loadappfwd

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtils {

    @RequiresApi(Build.VERSION_CODES.S)
    private fun buildNotification(
        status: String,
        fileName: String, context: Context
    ): Notification {

        val notificationBulider = NotificationCompat.Builder(
            context,
            Constants.CHANNEL_ID
        )


        notificationBulider.apply {

            setSmallIcon(R.drawable.ic_launcher_foreground)
            priority = NotificationCompat.PRIORITY_HIGH
            setContentTitle(context.getString(R.string.notification_title))
            setContentText(context.getString(R.string.notification_description))
            addAction(
                R.drawable.ic_launcher_background,
                context.getString(R.string.notification_button),
                createPentingIntent(status, fileName, context)
            )


        }

        return notificationBulider.build()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun createPentingIntent(
        status: String,
        fileName: String, context: Context
    ): PendingIntent {

        val intent = Intent(context, DetailsActivity::class.java)

        intent.putExtra(Constants.EXTRA_STATUS, status)
        intent.putExtra(Constants.EXTRA_FILE_NAME, fileName)

        return PendingIntent.getActivity(context, 5, intent, PendingIntent.FLAG_MUTABLE)

    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun publishNotification(
        status: String,
        fileName: String, context: Context
    ) {

        val managerCompat = NotificationManagerCompat.from(context)

        managerCompat.apply {

            notify(20, buildNotification(status, fileName, context))
        }
    }
}