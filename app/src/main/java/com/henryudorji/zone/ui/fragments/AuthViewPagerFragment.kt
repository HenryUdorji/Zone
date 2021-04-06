package com.henryudorji.zone.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.henryudorji.zone.R
import com.henryudorji.zone.adapter.ViewPagerAdapter
import com.henryudorji.zone.databinding.FragmentAuthViewpagerBinding

//
// Created by Henry Udorji on 4/5/2021.
//
class AuthViewPagerFragment: Fragment(R.layout.fragment_auth_viewpager) {
    private lateinit var binding: FragmentAuthViewpagerBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthViewpagerBinding.bind(view)

        setupViewPager()
    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf(
            SignInFragment(),
            SignUpFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
        tabLayoutMediator = TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position) {
                    0 -> { tab.text = "Sign in" }
                    1 -> { tab.text = "Sign up" }
                }
            })
        tabLayoutMediator.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}