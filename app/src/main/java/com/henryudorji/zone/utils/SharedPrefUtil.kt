package com.henryudorji.zone.utils

import com.henryudorji.zone.ZoneApplication
import com.henryudorji.zone.data.model.User
import com.henryudorji.zone.utils.Constants.EMAIL
import com.henryudorji.zone.utils.Constants.IS_CHECKED
import com.henryudorji.zone.utils.Constants.PASSWORD

//
// Created by Henry Udorji on 3/13/2021.
//
object SharedPrefUtil {

    fun putStringInPref(key: String, value: String) {
        ZoneApplication.sharedPref.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun putBooleanInPref(key: String, value: Boolean) {
        ZoneApplication.sharedPref.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun getBooleanFromPref(key: String): Boolean {
        return ZoneApplication.sharedPref.getBoolean(key, false)
    }

    fun getStringFromPref(key: String): String {
        return ZoneApplication.sharedPref.getString(key, "")!!
    }

    fun putLoginDetailsInPref(email: String, password: String, isChecked: Boolean) {
        ZoneApplication.sharedPref.edit().apply {
            putString(EMAIL, email)
            putString(PASSWORD, password)
            putBoolean(IS_CHECKED, isChecked)
            apply()
        }
    }

}