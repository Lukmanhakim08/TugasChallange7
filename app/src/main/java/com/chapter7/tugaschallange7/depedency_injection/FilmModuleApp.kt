package com.chapter7.tugaschallange7.depedency_injection

import android.content.Context
import com.chapter7.tugaschallange7.mater.roomdatabase.FilmFavoriteDao
import com.chapter7.tugaschallange7.mater.roomdatabase.FilmFavoriteDatabase
import com.chapter7.tugaschallange7.network.ApiFilmService
import com.chapter7.tugaschallange7.network.ApiUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FilmModuleApp {
    //base url for user API
    private const val BASE_URL = "https://6254436219bc53e2347b9583.mockapi.io/"

    //base url for ghibli film API
    private const val BASE_URL_2 = "https://ghibliapi.herokuapp.com"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    @Provides
    @Singleton
    fun provideRetrofitForUser(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideUserFromApi(retrofit: Retrofit): ApiUserService =
        retrofit.create(ApiUserService::class.java)

    //retrofit module for ghibli film API
    @Provides
    @Singleton
    @Named("FILM_RETROFIT")
    fun provideRetrofitForFilm(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    @Named("FILM_DATA")
    fun provideFilmFromApi(@Named("FILM_RETROFIT") retrofit: Retrofit):
            ApiFilmService = retrofit.create(ApiFilmService::class.java)

    @Provides
    @Singleton
    fun provideFavoriteFilmDatabase(@ApplicationContext context: Context): FilmFavoriteDatabase =
        FilmFavoriteDatabase.getInstance(context)!!

    @Provides
    @Singleton
    fun provideFavoriteFilmDao(filmFavoriteDatabase: FilmFavoriteDatabase): FilmFavoriteDao =
        filmFavoriteDatabase.filmFavoriteDao()
}