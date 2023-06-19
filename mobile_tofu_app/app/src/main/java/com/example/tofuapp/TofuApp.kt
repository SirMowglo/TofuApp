package com.example.tofuapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TofuApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}