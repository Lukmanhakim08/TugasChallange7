package com.chapter7.tugaschallange7.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataFilmResponseItem(
    val description: String,
    val director: String,
    val id: String,
    val image: String,
    val locations: List<String>,
    val movie_banner: String,
    val original_title: String,
    val original_title_romanised: String,
    val people: List<String>,
    val producer: String,
    val release_date: String,
    val rt_score: String,
    val running_time: String,
    val species: List<String>,
    val title: String,
    val url: String,
    val vehicles: List<String>
): Parcelable
