package com.tenminuteschool.localstatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.tenminuteschool.localstatemanager.databinding.ActivityMoreDetailsBinding
import kotlinx.coroutines.flow.collectLatest

class MoreDetails : AppCompatActivity() {

    lateinit var binding: ActivityMoreDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

            onBackPressed()
        }
        binding.mainViewBtn.setOnClickListener {

            startActivity(Intent(this@MoreDetails, MainActivity::class.java))
        }

        observeNow()



    }
    private fun observeNow() {
        lifecycleScope.launchWhenStarted {
            LocalEventManager.eventObserver.collectLatest {
                val event = LocalEventManager.hasStateChanged(LocalEventManager.State.MORE_DETAILS_HAS_VALUE)
                if(event.hasEvent){
                    Log.d(TAG, "observeNow: has event ${event.state.name}: ${event.data}")
                    binding.textView.text = "From Details with: ${event.data}"
                }

            }
        }

    }

    override fun onRestart() {
        super.onRestart()
        LocalEventManager.addEvent(LocalEventManager.State.MORE_DETAILS_RESTART, "More details has a restart event")
    }
}