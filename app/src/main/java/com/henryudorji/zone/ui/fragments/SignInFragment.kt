package com.henryudorji.zone.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.henryudorji.zone.R
import com.henryudorji.zone.databinding.ForgotPasswordDialogBinding
import com.henryudorji.zone.databinding.FragmentSignInBinding
import com.henryudorji.zone.databinding.LoadingDialogBinding
import com.henryudorji.zone.utils.AppUtil
import com.henryudorji.zone.utils.Constants.EMAIL
import com.henryudorji.zone.utils.Constants.IS_CHECKED
import com.henryudorji.zone.utils.Constants.PASSWORD
import com.henryudorji.zone.utils.SharedPrefUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

//
// Created by Henry on 3/14/2021.
//
class SignInFragment: Fragment(R.layout.fragment_sign_in) {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignInBinding
    private lateinit var dialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        auth = FirebaseAuth.getInstance()


        initViews()
    }

    private fun initViews() {
        if (SharedPrefUtil.getBooleanFromPref(IS_CHECKED)) {
            binding.rememberMeCheck.isChecked = true
            binding.email.setText(SharedPrefUtil.getStringFromPref(EMAIL))
            binding.password.setText(SharedPrefUtil.getStringFromPref(PASSWORD))
        }else {
            binding.rememberMeCheck.isChecked = false
        }


        binding.signInBtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() and password.isNotEmpty()) {
                loginUser(email, password)
            }else {
                AppUtil.showLongSnackbar(binding.root, "No field should be empty")
            }
        }

        binding.rememberMeCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (isChecked) {
                SharedPrefUtil.putLoginDetailsInPref(email, password, isChecked)
            }else {
                SharedPrefUtil.putLoginDetailsInPref("", "", false)
            }
        }

        binding.forgotPasswordText.setOnClickListener {
            showResetPasswordDialog()
        }
    }

    private fun loginUser(email: String, password: String) = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            showProgressDialog()
        }
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            auth.addAuthStateListener {
                val currentUser = it.currentUser
                CoroutineScope(Dispatchers.Main).launch {
                    if (currentUser?.isEmailVerified == true) {
                        dialog.dismiss()
                        findNavController().navigate(R.id.action_authViewPagerFragment_to_homeFragment)
                    }else {
                        dialog.dismiss()
                        AppUtil.showLongSnackbar(binding.root, "Please Verify your email address")
                    }
                }
            }
        }catch (e: Exception) {
            withContext(Dispatchers.Main) {
                dialog.dismiss()
                AppUtil.showLongSnackbar(binding.root, e.message.toString())
            }
        }

    }

    private fun showProgressDialog() {
        dialog = Dialog(requireContext())
        val dialogBinding: LoadingDialogBinding = LoadingDialogBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun showResetPasswordDialog() {
        dialog = Dialog(requireContext())
        val dialogBinding: ForgotPasswordDialogBinding = ForgotPasswordDialogBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialogBinding.continueBtn.setOnClickListener {
            dialogBinding.continueBtn.visibility = View.GONE
            dialogBinding.progressBar.visibility = View.VISIBLE

            val email = dialogBinding.email.text.toString()
            if (email.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        auth.sendPasswordResetEmail(email).await()
                        withContext(Dispatchers.Main) {
                            dialog.dismiss()
                            AppUtil.showLongSnackbar(binding.root, "Password reset email sent successfully")
                        }
                    }catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            dialog.dismiss()
                            AppUtil.showLongSnackbar(binding.root, e.message.toString())
                        }
                    }
                }
            }else {
                AppUtil.showShortSnackbar(binding.root, "Please enter a valid email address")
                dialogBinding.continueBtn.visibility = View.VISIBLE
                dialogBinding.progressBar.visibility = View.GONE
            }
        }
    }
}
