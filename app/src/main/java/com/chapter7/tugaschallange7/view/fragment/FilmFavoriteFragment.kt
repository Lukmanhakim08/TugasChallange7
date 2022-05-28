package com.chapter7.tugaschallange7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.chapter7.tugaschallange7.R
import com.chapter7.tugaschallange7.databinding.FragmentFilmFavoriteBinding
import com.chapter7.tugaschallange7.view.adapter.AdapterFavoriteFilm
import com.chapter7.tugaschallange7.viewmodel.ViewModelFilmFavorite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmFavoriteFragment : Fragment(R.layout.fragment_film_favorite) {
    private var filmFavoriteFragmentBinding: FragmentFilmFavoriteBinding? = null
    private lateinit var adapter: AdapterFavoriteFilm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFilmFavoriteBinding.bind(view)
        filmFavoriteFragmentBinding = binding

        iniView()
    }

    private fun iniView(){
        adapter = AdapterFavoriteFilm {
            val clickedFilm = bundleOf("FAVORITEFILMDATA" to it)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_filmFavoriteFragment_to_detailFilmFragment)
        }
        filmFavoriteFragmentBinding!!.rvFavoriteGhibliFilm.layoutManager = LinearLayoutManager(requireContext())
        filmFavoriteFragmentBinding!!.rvFavoriteGhibliFilm.adapter = adapter

        val viewModelFilmFavorite = ViewModelProvider(this).get(ViewModelFilmFavorite::class.java)
        viewModelFilmFavorite.favoriteFilm.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                adapter.setDataFilmFavorite(it)
                adapter.notifyDataSetChanged()
            } else {
                filmFavoriteFragmentBinding!!.favoriteNoDataAnimation.setAnimation(R.raw.no_favorite_film_data)
            }
        }
    }
}