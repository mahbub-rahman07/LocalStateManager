package com.tenminuteschool.localstatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tenminuteschool.localstatemanager.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backToActivity.setOnClickListener {
            onBackPressed()

        }

        binding.goToMoreDetails.setOnClickListener {
            LocalEventManager.addEvent(LocalEventManager.State.MORE_DETAILS_HAS_VALUE,11)

            startActivity(Intent(this@DetailsActivity, MoreDetails::class.java))
        }


    }

    override fun onStop() {
        super.onStop()
        LocalEventManager.addEvent(LocalEventManager.State.DETAILS_VISITED,"Details page visited")
    }


}