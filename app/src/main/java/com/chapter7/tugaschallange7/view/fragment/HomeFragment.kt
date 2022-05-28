package com.chapter7.tugaschallange7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.FragmentHomeBinding
import com.chapter7.tugaschallange7.mater.datastore.LoginUserManager
import com.chapter7.tugaschallange7.view.adapter.AdapterFilm
import com.chapter7.tugaschallange7.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var homeFragmentBinding: FragmentHomeBinding? = null
    private lateinit var loginUserManager: LoginUserManager
    private lateinit var adapter: AdapterFilm


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        homeFragmentBinding = binding

        initView()
    }

    private fun initView(){
        loginUserManager = LoginUserManager(requireContext())
        loginUserManager.username.asLiveData().observe(viewLifecycleOwner){
            homeFragmentBinding!!.welcomeText.text = "Welcome, $it"
        }
        loginUserManager.image.asLiveData().observe(viewLifecycleOwner){ result ->
            Glide.with(homeFragmentBinding!!.homeToProfile.context)
                .load(result)
                .error(R.drawable.profile)
                .override(100, 100)
                .into(homeFragmentBinding!!.homeToProfile)
        }

        adapter = AdapterFilm {
            val clikFilm = bundleOf("FILMDATA" to it)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_detailFilmFragment, clikFilm)
        }
        homeFragmentBinding!!.rvFilm.layoutManager = LinearLayoutManager(requireContext())
        homeFragmentBinding!!.rvFilm.adapter = adapter

        val viewModelFilm = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModelFilm.film.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                adapter.setDataFilm(it)
                adapter.notifyDataSetChanged()
            }
        }

        homeFragmentBinding!!.homeToProfile.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_profileFragment)
        }

        homeFragmentBinding!!.homeToFavorite.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_filmFavoriteFragment)
        }
    }
}