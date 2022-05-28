package com.chapter7.tugaschallange7.network

import com.chapter7.tugaschallange7.model.DataUserResponseItem
import com.chapter7.tugaschallange7.model.PostNewUser
import com.chapter7.tugaschallange7.model.RequesUser
import retrofit2.Call
import retrofit2.http.*

interface ApiUserService {
    @GET("user")
    suspend fun getAllUser(): List<DataUserResponseItem>

    @POST("user")
    fun addDataUser(@Body reqUser: RequesUser): Call<PostNewUser>

    @PUT("user/{id}")
    fun updateDataUser(
        @Path("id") id: String,
        @Body request: RequesUser
    ): Call<List<DataUserResponseItem>>
}