package com.example.rafaelanastacioalves.zemoga.application

import android.app.Application
import com.example.rafaelanastacioalves.zemoga.R

class TestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_AppCompat)

    }

    override fun onTerminate() {
        super.onTerminate()
    }
}