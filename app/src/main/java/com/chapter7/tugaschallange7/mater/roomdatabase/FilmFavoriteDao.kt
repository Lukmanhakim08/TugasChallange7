package com.chapter7.tugaschallange7.mater.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chapter7.tugaschallange7.model.FavoriteFilm

@Dao
interface FilmFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFilm(favoriteFilm: FavoriteFilm)

    @Query("SELECT * FROM favorite_film")
    fun getFavoriteFilm(): List<FavoriteFilm>

    @Query("DELETE FROM favorite_film WHERE id = :id")
    suspend fun deleteFavoriteFilmById(id: Int)
}