package com.henryudorji.zone.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.henryudorji.zone.R
import com.henryudorji.zone.data.model.User
import com.henryudorji.zone.databinding.FragmentAuthViewpagerBinding
import com.henryudorji.zone.databinding.FragmentSignUpBinding
import com.henryudorji.zone.databinding.LoadingDialogBinding
import com.henryudorji.zone.utils.AppUtil
import com.henryudorji.zone.utils.Constants.USERS_COLLECTION
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

//
// Created by Henry on 3/14/2021.
//
class SignUpFragment: Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private val usersCollectionRef = Firebase.firestore.collection(USERS_COLLECTION)
    private lateinit var dialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        initViews();
    }

    private fun initViews() {
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(email: CharSequence?, start: Int, before: Int, count: Int) {
                if (email.toString().isEmpty() or !email.toString().contains("@")
                    || !email.toString().contains(".")) {
                    binding.textInputEmail.error = "Please enter a valid email address"
                }else {
                    binding.textInputEmail.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                usersCollectionRef.whereEqualTo("username", s.toString())
                    .addSnapshotListener { querySnapshot, exception ->
                    exception?.let {
                        AppUtil.showLongSnackbar(binding.root, it.message.toString())
                        return@addSnapshotListener
                    }
                    querySnapshot?.let {
                        for (document in it.documents) {
                            val username = document.getString("username")

                            if (username.equals(s.toString())) {
                                binding.textInputUsername.error = "Username already exist"
                            }else {
                                binding.textInputUsername.error = null
                            }
                        }
                        if (it.size() == 0) {
                            binding.textInputUsername.error = null
                        }
                    }
                }
            }

        })
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(password: CharSequence?, start: Int, before: Int, count: Int) {
                if (password.toString().length <= 5) {
                    binding.textInputPassword.error = "Password length should be six or more characters"
                }else {
                    binding.textInputPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.signUpBtn.setOnClickListener {
            val email = binding.email.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() and username.isNotEmpty() and password.isNotEmpty()) {
                createUserAccount(email, username, password)
            }else {
                AppUtil.showLongSnackbar(binding.root, "Fields cannot be empty")
            }

        }
    }

    private fun createUserAccount(
        email: String,
        username: String,
        password: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            showProgressDialog()
        }

        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            auth.addAuthStateListener {
                val currentUser = it.currentUser
                if (currentUser != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            currentUser.sendEmailVerification().await()

                            val user = User(currentUser.uid, "@$username",
                                "", currentUser.email, "")
                            saveUserDetails(user)

                            withContext(Dispatchers.Main) {
                                dialog.dismiss()
                                AppUtil.showLongSnackbar(binding.root, getString(R.string.verify_email_msg))
                                clearSignUpForm()
                            }
                        }catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                dialog.dismiss()
                                AppUtil.showLongSnackbar(binding.root, e.message.toString())
                            }
                        }
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

    private fun saveUserDetails(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            usersCollectionRef.add(user).await()
        }catch (e: Exception) {
            withContext(Dispatchers.Main) {
                AppUtil.showLongSnackbar(binding.root, e.message.toString())
            }
        }
    }

    private fun clearSignUpForm() {
        binding.email.setText("")
        binding.username.setText("")
        binding.password.setText("")
    }

    private fun showProgressDialog() {
        dialog = Dialog(requireContext())
        val dialogBinding: LoadingDialogBinding = LoadingDialogBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}
