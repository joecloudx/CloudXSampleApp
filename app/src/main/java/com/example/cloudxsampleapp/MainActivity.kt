package com.example.cloudxsampleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cloudxsampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bannerAdButton.setOnClickListener {
            val intent = Intent(this, BannerAdActivity::class.java)
            startActivity(intent)
        }

        binding.interstitialAdButton.setOnClickListener {
            val intent = Intent(this, InterstitialAdActivity::class.java)
            startActivity(intent)
        }
    }
}