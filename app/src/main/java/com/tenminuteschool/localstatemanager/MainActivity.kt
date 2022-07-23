package com.tenminuteschool.localstatemanager

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.red
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.tenminuteschool.localstatemanager.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeNow()

        binding.detailsBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, DetailsActivity::class.java))
        }

    }

    private fun observeNow() {
        lifecycleScope.launchWhenStarted {
            LocalEventManager.eventObserver.collectLatest {
                val eventTop = LocalEventManager.hasStateChanged(LocalEventManager.State.DETAILS_VISITED)
                val eventBottom = LocalEventManager.hasStateChanged(LocalEventManager.State.MORE_DETAILS_RESTART)
                if(eventTop.hasEvent){
                    Log.d(TAG, "observeNow: has eventTop event ${eventTop.state.name}: ${eventTop.data}")
                    binding.topTv.text = "${eventTop.data}"
                    binding.topTv.setTextColor(Color.RED)
                }
                if(eventBottom.hasEvent){
                    Log.e(TAG, "observeNow: has eventBottom event: ${eventBottom.state.name}: ${eventBottom.data}")
                    binding.bottomTv.text = "${eventBottom.data}"
                    binding.bottomTv.setTextColor(Color.GREEN)
                }
                Log.e(TAG, "observeNow: events: ${eventTop.state.name}: ${eventTop.data}")
            }
        }

    }
}