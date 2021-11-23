package com.lohanna.nekopics.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lohanna.nekopics.R
import com.lohanna.nekopics.application.NekoPicsApp.Companion.getInternetStatus
import com.lohanna.nekopics.utils.hideActionBar

class SplashFragment: Fragment(R.layout.fragment_splash) {

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideActionBar(activity as AppCompatActivity)

        Handler().postDelayed({

            when(getInternetStatus()){
                true -> findNavController().navigate(R.id.action_splashFragment_to_onlineFragment)
                false -> findNavController().navigate(R.id.action_splashFragment_to_offlineFragment)
            }

        }, 2000)

    }

}