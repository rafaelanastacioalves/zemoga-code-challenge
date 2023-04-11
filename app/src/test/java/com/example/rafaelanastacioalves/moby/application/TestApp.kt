package com.example.rafaelanastacioalves.moby.application

import android.app.Application
import com.example.rafaelanastacioalves.moby.R

class TestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_AppCompat)

    }

    override fun onTerminate() {
        super.onTerminate()
    }
}