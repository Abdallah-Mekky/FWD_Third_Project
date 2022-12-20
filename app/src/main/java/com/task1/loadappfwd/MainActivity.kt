package com.task1.loadappfwd

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.task1.loadappfwd.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var receiver: BroadcastReceiver
    lateinit var url: String
    lateinit var fileName: String
    private var downloadID by Delegates.notNull<Long>()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        broadCast()
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        listenerRadioButton()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)
    }


    private fun broadCast() {
        receiver = object : BroadcastReceiver() {
            @RequiresApi(Build.VERSION_CODES.S)
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

                val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

                val query = DownloadManager.Query()
                query.setFilterById(id!!)

                val cursor = downloadManager.query(query)
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    var downloadStatus = "Failed"
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(index)) downloadStatus =
                        "Success"

                    Toast.makeText(applicationContext, "Downloaded Successfully", Toast.LENGTH_LONG)
                        .show()

                    binding.downloadButton.buttonState = ButtonState.Completed

                    NotificationUtils.publishNotification(
                        downloadStatus,
                        fileName,
                        this@MainActivity
                    )


                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun listenerRadioButton() {

        binding.downloadButton.setOnClickListener {

            if (binding.radioButtonGlide.isChecked) {

                url = getString(R.string.Glide)
                fileName = getString(R.string.radio_button_glide)
                binding.downloadButton.buttonState = ButtonState.Loading
                download()
            } else if (binding.radioButtonLoadApp.isChecked) {

                url = getString(R.string.LoadApp)
                fileName = getString(R.string.radio_button_loadApp)
                binding.downloadButton.buttonState = ButtonState.Loading
                download()
            } else if (binding.radioButtonRetrofit.isChecked) {

                url = getString(R.string.Retrofit)
                fileName = getString(R.string.radio_button_retorfit)
                binding.downloadButton.buttonState = ButtonState.Loading
                download()
            } else {

                Toast.makeText(this, "Please Select  option", Toast.LENGTH_LONG).show()
            }

        }
    }

}