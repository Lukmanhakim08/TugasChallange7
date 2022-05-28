package com.chapter7.tugaschallange7.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.FragmentSplashBinding
import com.chapter7.tugaschallange7.mater.datastore.LoginUserManager

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private var splashFragmentBinding : FragmentSplashBinding? = null
    private lateinit var loginUserManager: LoginUserManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)
        splashFragmentBinding = binding
        loginUserManager = LoginUserManager(requireContext())

        Handler(Looper.getMainLooper()).postDelayed({
           loginUserManager.boolean.asLiveData().observe(viewLifecycleOwner) {
               if (it == true){
                   Navigation.findNavController(view)
                       .navigate(R.id.action_splashFragment_to_homeFragment)
               } else{
                   Navigation.findNavController(view)
                       .navigate(R.id.action_splashFragment_to_loginFragment)
               }
           }
        },2000)
    }
}