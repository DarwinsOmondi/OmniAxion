package com.example.omniaxion

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OmniAxionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            timber.log.Timber.plant(timber.log.Timber.DebugTree())
        }
    }
}
