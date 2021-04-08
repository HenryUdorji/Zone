package com.henryudorji.zone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.henryudorji.zone.R
import com.henryudorji.zone.databinding.FragmentHomeBinding
import com.henryudorji.zone.utils.AppUtil

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser == null) {
            findNavController().navigate(R.id.action_homeFragment_to_authViewPagerFragment)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)


        initViews()
    }

    private fun initViews() {
        binding.bottomNavFab.setOnClickListener {
            AppUtil.showShortSnackbar(binding.root, "Fab clicked")
        }

        binding.bottomNav.background = null
        binding.bottomNav.menu.getItem(2).isEnabled = false
    }

}