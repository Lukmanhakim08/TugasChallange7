package com.chapter7.tugaschallange7.roomdatabase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chapter7.tugaschallange7.mater.roomdatabase.FilmFavoriteDao
import com.chapter7.tugaschallange7.mater.roomdatabase.FilmFavoriteDatabase
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilmFavoriteDatabaseTest : TestCase() {

    private lateinit var db : FilmFavoriteDatabase
    private lateinit var dao : FilmFavoriteDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FilmFavoriteDatabase::class.java).build()
        dao = db.filmFavoriteDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun getFilmFavoriteTest(){
        val result = dao.getFavoriteFilm()
    }
}