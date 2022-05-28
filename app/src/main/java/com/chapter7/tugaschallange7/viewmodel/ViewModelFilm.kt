package com.chapter7.tugaschallange7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapter7.tugaschallange7.model.DataFilmResponseItem
import com.chapter7.tugaschallange7.network.ApiFilmService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ViewModelFilm @Inject constructor(@Named("FILM_DATA") api: ApiFilmService) : ViewModel() {
    private val liveDataFilm = MutableLiveData<List<DataFilmResponseItem>> ()
    val film: LiveData<List<DataFilmResponseItem>> = liveDataFilm

    init {
        viewModelScope.launch {
            val dataFilm = api.getAllGhibliFilms()
            delay(2000)
            liveDataFilm.value = dataFilm
        }
    }
}