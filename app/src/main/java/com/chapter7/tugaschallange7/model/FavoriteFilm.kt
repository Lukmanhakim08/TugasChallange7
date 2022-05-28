package com.chapter7.tugaschallange7.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_film")
data class FavoriteFilm(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val judulInggris: String?,
    val judulOriginal: String?,
    val judulRomaji: String?,
    val director: String?,
    val producer: String?,
    val releaseDate: String?,
    val rating: String?,
    val description: String?,
    val image: String?,
    val idFilm: String?
): Parcelable
