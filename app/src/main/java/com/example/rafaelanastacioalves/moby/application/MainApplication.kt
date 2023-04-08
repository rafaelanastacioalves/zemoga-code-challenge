package com.example.rafaelanastacioalves.moby.application

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

import com.example.rafaelanastacioalves.moby.BuildConfig
import com.example.rafaelanastacioalves.moby.repository.database.AppDataBase
import com.squareup.picasso.Picasso


class MainApplication : Application() {
    override fun onCreate() {
        setupDB()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate()
    }

    private fun setupDB() {
        AppDataBase.setupAtApplicationStartup(this)
    }

    /**
     * A tree which logs important information for crash reporting.
     */

}
