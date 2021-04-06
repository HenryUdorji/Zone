package com.henryudorji.zone.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.henryudorji.zone.R
import com.henryudorji.zone.databinding.FragmentSplashBinding
import com.henryudorji.zone.utils.Constants.IS_NOT_FIRST_LAUNCH
import com.henryudorji.zone.utils.SharedPrefUtil

//
// Created by Henry Udorji on 3/13/2021.
//
class SplashFragment: Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        Handler().postDelayed({
            if (currentUser == null) {
                findNavController().navigate(R.id.action_splashFragment_to_authViewPagerFragment)
            }else if(!SharedPrefUtil.getBooleanFromPref(IS_NOT_FIRST_LAUNCH)) {
                findNavController().navigate(R.id.action_splashFragment_to_introFragment)
            }else {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }

        }, 2000)
    }
}