package com.chapter7.tugaschallange7.mater.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chapter7.tugaschallange7.model.FavoriteFilm

@Database(entities = [FavoriteFilm::class], version = 1, exportSchema = false)
abstract class FilmFavoriteDatabase : RoomDatabase() {
    abstract fun filmFavoriteDao() : FilmFavoriteDao

    companion object{
        private var INSTANCE: FilmFavoriteDatabase? = null

        fun getInstance(context: Context): FilmFavoriteDatabase? {
            if (INSTANCE == null){
                synchronized(FilmFavoriteDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FilmFavoriteDatabase::class.java, "FilmFavorite.db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}