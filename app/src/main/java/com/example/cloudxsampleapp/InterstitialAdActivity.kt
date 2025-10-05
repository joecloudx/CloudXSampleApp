package com.example.cloudxsampleapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloudxsampleapp.databinding.ActivityInterstitialAdBinding
import io.cloudx.sdk.CloudX
import io.cloudx.sdk.CloudXAd
import io.cloudx.sdk.CloudXError
import io.cloudx.sdk.CloudXInterstitialAd
import io.cloudx.sdk.CloudXInterstitialListener

class InterstitialAdActivity : AppCompatActivity(), CloudXInterstitialListener {
    private lateinit var interstitialAd: CloudXInterstitialAd
    private lateinit var binding: ActivityInterstitialAdBinding
    private val events = mutableListOf<Event>()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterstitialAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializeEvents()
        setupRecyclerView()
        createInterstitialAd()

        binding.showAdButton.setOnClickListener {
            showInterstitialAd()
        }
    }

    private fun initializeEvents() {
        events.add(Event("onAdLoaded", "Pending"))
        events.add(Event("onAdLoadFailed", "Pending"))
        events.add(Event("onAdDisplayed", "Pending"))
        events.add(Event("onAdDisplayFailed", "Pending"))
        events.add(Event("onAdHidden", "Pending"))
        events.add(Event("onAdClicked", "Pending"))
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter(events)
        binding.eventRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@InterstitialAdActivity)
            adapter = eventAdapter
        }
    }

    private fun updateEvent(eventName: String, status: String, details: String = "") {
        val event = events.find { it.name == eventName }
        event?.let {
            it.status = status
            it.details = details
            eventAdapter.notifyItemChanged(events.indexOf(it))
        }
    }

    private fun createInterstitialAd() {
        // Create interstitial ad
        interstitialAd = CloudX.createInterstitial("your-interstitial-placement-name")

        // Set listener
        interstitialAd.listener = this

        // Load the ad
        interstitialAd.load()
    }

    private fun showInterstitialAd() {
        if (interstitialAd.isAdReady) {
            interstitialAd.show()
        } else {
            Log.w("CloudX", "Interstitial ad not ready yet")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitialAd.destroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // CloudXInterstitialListener callbacks
    override fun onAdLoaded(cloudXAd: CloudXAd) {
        updateEvent("onAdLoaded", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "✅ Interstitial ad loaded successfully from ${cloudXAd.bidderName}")
    }

    override fun onAdLoadFailed(cloudXError: CloudXError) {
        updateEvent("onAdLoadFailed", "Failure", cloudXError.effectiveMessage)
        Log.e("CloudX", "❌ Interstitial ad failed to load: ${cloudXError.effectiveMessage}")
    }

    override fun onAdDisplayed(cloudXAd: CloudXAd) {
        updateEvent("onAdDisplayed", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "👀 Interstitial ad shown from ${cloudXAd.bidderName}")
    }

    override fun onAdDisplayFailed(cloudXError: CloudXError) {
        updateEvent("onAdDisplayFailed", "Failure", cloudXError.effectiveMessage)
        Log.e("CloudX", "❌ Interstitial ad failed to show: ${cloudXError.effectiveMessage}")
    }

    override fun onAdHidden(cloudXAd: CloudXAd) {
        updateEvent("onAdHidden", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "🔚 Interstitial ad hidden from ${cloudXAd.bidderName}")
        // Reload for next use
        createInterstitialAd()
    }

    override fun onAdClicked(cloudXAd: CloudXAd) {
        updateEvent("onAdClicked", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "👆 Interstitial ad clicked from ${cloudXAd.bidderName}")
    }
}