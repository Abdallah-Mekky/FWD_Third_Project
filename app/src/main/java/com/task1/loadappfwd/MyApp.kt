package com.task1.loadappfwd

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class MyApp :Application() {


    override fun onCreate() {
        super.onCreate()

        createNotificatioChannel()

    }

    private fun createNotificatioChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){


            val channel = NotificationChannel(
                Constants.CHANNEL_ID,getString(R.string.notification_channel_title),
                NotificationManager.IMPORTANCE_DEFAULT)

            val manger = getSystemService(NotificationManager::class.java) as NotificationManager

            manger.createNotificationChannel(channel)
        }


    }
}