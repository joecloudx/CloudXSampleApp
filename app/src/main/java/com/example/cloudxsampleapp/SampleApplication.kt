package com.example.cloudxsampleapp

import android.app.Application
import android.util.Log
import io.cloudx.sdk.CloudX
import io.cloudx.sdk.CloudXError
import io.cloudx.sdk.CloudXInitializationListener
import io.cloudx.sdk.CloudXInitializationParams
import io.cloudx.sdk.CloudXInitializationServer
import io.cloudx.sdk.CloudXLogLevel

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Enable debug logging for troubleshooting
        CloudX.setLoggingEnabled(true)
        CloudX.setMinLogLevel(CloudXLogLevel.DEBUG)

        // Initialize with app key and optional hashed user ID
        CloudX.initialize(
            initParams = CloudXInitializationParams(
                appKey = "2hs4DV7XUHDEnfyIuQkAD",
                initServer = CloudXInitializationServer.Staging,
                hashedUserId = "hashed-user-id-optional"
            ),
            listener = object : CloudXInitializationListener {
                override fun onInitialized() {
                    Log.d("CloudX", "✅ CloudX SDK initialized successfully")
                }

                override fun onInitializationFailed(cloudXError: CloudXError) {
                    Log.e("CloudX", "❌ Failed to initialize CloudX SDK: ${cloudXError.effectiveMessage}")
                }
            }
        )
    }
}