package com.henryudorji.zone

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

//
// Created by Henry Udorji on 3/12/2021.
//
class ZoneApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    companion object {
        lateinit var sharedPref: SharedPreferences
    }
}