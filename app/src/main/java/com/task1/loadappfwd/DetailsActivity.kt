package com.task1.loadappfwd

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.task1.loadappfwd.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)


        binding.status.text = intent.getStringExtra(Constants.EXTRA_STATUS)
        binding.title.text = intent.getStringExtra(Constants.EXTRA_FILE_NAME)


        if (binding.status.text == "Success") binding.status.setTextColor(getColor(R.color.colorAccent))
        else binding.status.setTextColor(getColor(R.color.error))


        binding.customButton.setOnClickListener {
            finish()
        }


    }

}