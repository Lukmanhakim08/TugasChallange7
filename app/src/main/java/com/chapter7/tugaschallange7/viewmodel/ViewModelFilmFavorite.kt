package com.chapter7.tugaschallange7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapter7.tugaschallange7.mater.roomdatabase.FilmFavoriteDao
import com.chapter7.tugaschallange7.model.FavoriteFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFilmFavorite @Inject constructor(filmFavoriteDao: FilmFavoriteDao) : ViewModel() {
    private val liveDataFilmFavorite = MutableLiveData<List<FavoriteFilm>>()
    var favoriteFilm : LiveData<List<FavoriteFilm>> = liveDataFilmFavorite
    private val dao = filmFavoriteDao

    init {
        viewModelScope.launch {
            val dataFilmFavorite = filmFavoriteDao.getFavoriteFilm()
            liveDataFilmFavorite.value = dataFilmFavorite
        }
    }

    fun insertNewFilmFavorite(favoriteFilm: FavoriteFilm){
        viewModelScope.launch {
            dao.insertFavoriteFilm(favoriteFilm)
        }
    }

    fun deleteFilmFavorite(id: Int) {
        viewModelScope.launch {
            dao.deleteFavoriteFilmById(id)
        }
    }

}