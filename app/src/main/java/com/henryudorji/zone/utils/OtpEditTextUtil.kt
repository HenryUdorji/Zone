package com.henryudorji.zone.utils
/*
package com.henryudorji.zone.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.henryudorji.zone.R

//
// Created by  on 3/13/2021.
//
class OtpEditTextUtil {

    class GenericKeyEvent internal constructor(
        private val currentView: AppCompatEditText,
        private val previousView: AppCompatEditText?
        ): View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL
                && currentView.id != R.id.edit_otp1 && currentView.text!!.isEmpty()) {
                // If current is empty then previous EditText's number will be deleted
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }
    }

    class GenericTextWatcher internal constructor(
        private val currentView: View,
        private val nextView: View?
        ): TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val text = s.toString()
            when(currentView.id) {
                R.id.edit_otp1 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edit_otp2 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edit_otp3 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edit_otp4 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edit_otp5 -> if (text.length == 1) nextView!!.requestFocus()
                //R.id.edit_otp6 -> if (text.length == 1) nextView!!.requestFocus()
            }
        }
    }
}*/
