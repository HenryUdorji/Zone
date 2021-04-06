package com.henryudorji.zone.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.henryudorji.zone.R
import com.henryudorji.zone.databinding.FragmentIntroBinding
import com.henryudorji.zone.utils.Constants.IS_NOT_FIRST_LAUNCH
import com.henryudorji.zone.utils.SharedPrefUtil

//
// Created by Henry Udorji on 3/13/2021.
//
class IntroFragment: Fragment(R.layout.fragment_intro) {
    private lateinit var binding: FragmentIntroBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIntroBinding.bind(view)

        binding.enterZoneBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_authViewPagerFragment)
            SharedPrefUtil.putBooleanInPref(IS_NOT_FIRST_LAUNCH, true)
        }
    }
}