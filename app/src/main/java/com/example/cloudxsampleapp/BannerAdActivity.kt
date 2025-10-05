package com.example.cloudxsampleapp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloudxsampleapp.databinding.ActivityBannerAdBinding
import io.cloudx.sdk.CloudX
import io.cloudx.sdk.CloudXAd
import io.cloudx.sdk.CloudXAdView
import io.cloudx.sdk.CloudXAdViewListener
import io.cloudx.sdk.CloudXError

class BannerAdActivity : AppCompatActivity(), CloudXAdViewListener {
    private lateinit var bannerAd: CloudXAdView
    private lateinit var binding: ActivityBannerAdBinding
    private val events = mutableListOf<Event>()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializeEvents()
        setupRecyclerView()
        createBannerAd()
    }

    private fun initializeEvents() {
        events.add(Event("onAdLoaded", "Pending"))
        events.add(Event("onAdLoadFailed", "Pending"))
        events.add(Event("onAdDisplayed", "Pending"))
        events.add(Event("onAdClicked", "Pending"))
        events.add(Event("onAdHidden", "Pending"))
        events.add(Event("onAdDisplayFailed", "Pending"))
        events.add(Event("onAdExpanded", "Pending"))
        events.add(Event("onAdCollapsed", "Pending"))
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter(events)
        binding.eventRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@BannerAdActivity)
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

    private fun createBannerAd() {
        // Create banner ad
        bannerAd = CloudX.createBanner("testbanner")

        // Set listener
        bannerAd.listener = this

        // Add to view hierarchy
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL

        // Add to your container view
        binding.bannerContainer.addView(bannerAd, layoutParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerAd.destroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // CloudXAdViewListener callbacks
    override fun onAdLoaded(cloudXAd: CloudXAd) {
        updateEvent("onAdLoaded", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "✅ Banner ad loaded successfully from ${cloudXAd.bidderName}")
    }

    override fun onAdLoadFailed(cloudXError: CloudXError) {
        updateEvent("onAdLoadFailed", "Failure", cloudXError.effectiveMessage)
        Log.e("CloudX", "❌ Banner ad failed to load: ${cloudXError.effectiveMessage}")
    }

    override fun onAdDisplayed(cloudXAd: CloudXAd) {
        updateEvent("onAdDisplayed", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "👀 Banner ad shown from ${cloudXAd.bidderName}")
    }

    override fun onAdClicked(cloudXAd: CloudXAd) {
        updateEvent("onAdClicked", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "👆 Banner ad clicked from ${cloudXAd.bidderName}")
    }

    override fun onAdHidden(cloudXAd: CloudXAd) {
        updateEvent("onAdHidden", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "🔚 Banner ad hidden from ${cloudXAd.bidderName}")
    }

    override fun onAdDisplayFailed(cloudXError: CloudXError) {
        updateEvent("onAdDisplayFailed", "Failure", cloudXError.effectiveMessage)
        Log.e("CloudX", "❌ Banner ad failed to display: ${cloudXError.effectiveMessage}")
    }

    override fun onAdExpanded(cloudXAd: CloudXAd) {
        updateEvent("onAdExpanded", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "📈 Banner ad expanded from ${cloudXAd.bidderName}")
    }

    override fun onAdCollapsed(cloudXAd: CloudXAd) {
        updateEvent("onAdCollapsed", "Success", cloudXAd.bidderName)
        Log.d("CloudX", "📉 Banner ad collapsed from ${cloudXAd.bidderName}")
    }
}