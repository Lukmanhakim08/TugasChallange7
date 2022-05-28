package com.chapter7.tugaschallange7.network

import com.chapter7.tugaschallange7.model.DataFilmResponseItem
import retrofit2.http.GET

interface ApiFilmService {
    @GET("films")
    suspend fun getAllGhibliFilms(): List<DataFilmResponseItem>

}