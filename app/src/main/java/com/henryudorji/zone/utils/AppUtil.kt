package com.henryudorji.zone.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.henryudorji.zone.databinding.LoadingDialogBinding

//
// Created by Hash on 4/6/2021.
//
object AppUtil {

    fun showLongSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showShortSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}