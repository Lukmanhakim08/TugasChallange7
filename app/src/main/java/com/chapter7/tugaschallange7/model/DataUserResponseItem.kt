package com.chapter7.tugaschallange7.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataUserResponseItem(
    val alamat: String,
    val email: String,
    val id: String,
    val image: String,
    val name: String,
    val password: String,
    val tanggal_lahir: String,
    val username: String
): Parcelable
