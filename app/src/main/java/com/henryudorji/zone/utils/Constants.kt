package com.henryudorji.zone.utils

//
// Created by HenryUdorji on 3/14/2021.
//
object Constants {
    const val PACKAGE_NAME = "com.henryudorji.zone"
    const val IS_NOT_FIRST_LAUNCH = "${PACKAGE_NAME}_isFirstLaunch"
    const val RESEND_WAIT_MILLIS: Long = 60000
    const val TICK_INTERVAL_MILLIS: Long = 1000
    const val CREDENTIAL_PICKER_REQUEST = 1
    const val SMS_CONSENT_REQUEST = 2

    //Firebase
    const val USERS_COLLECTION = "users"

    //SharedPref
    const val EMAIL = "${PACKAGE_NAME}_email"
    const val PASSWORD = "${PACKAGE_NAME}_password"
    const val IS_CHECKED = "${PACKAGE_NAME}_is_checked"
}