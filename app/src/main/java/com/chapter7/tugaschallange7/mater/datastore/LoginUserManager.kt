package com.chapter7.tugaschallange7.mater.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginUserManager(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore("login-prefs")

    companion object {
        val ADDRESS = preferencesKey<String>("ADDRESS")
        val EMAIL = preferencesKey<String>("EMAIL")
        val IDUSER = preferencesKey<String>("IDUSER")
        val IMAGE = preferencesKey<String>("IMAGE")
        val NAME = preferencesKey<String>("NAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val DATE_OF_BIRTH = preferencesKey<String>("DATE_OF_BIRTH")
        val USERNAME = preferencesKey<String>("USERNAME")
        val IMAGEPROFILE = preferencesKey<String>("IMAGEPROFILE")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveDataLogin(
        address: String,
        email: String,
        id: String,
        image: String,
        name: String,
        password: String,
        dateOfBirth: String,
        username: String
    ) {
        dataStore.edit {
            it[ADDRESS] = address
            it[EMAIL] = email
            it[IDUSER] = id
            it[IMAGE] = image
            it[NAME] = name
            it[PASSWORD] = password
            it[DATE_OF_BIRTH] = dateOfBirth
            it[USERNAME] = username
        }
    }

    suspend fun setImageProfile(string: String) {
        dataStore.edit {
            it[IMAGEPROFILE] = string
        }
    }

    suspend fun setBoolean(boolean: Boolean) {
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

    val address: Flow<String> = dataStore.data.map {
        it[ADDRESS] ?: ""
    }

    val email: Flow<String> = dataStore.data.map {
        it[EMAIL] ?: ""
    }

    val IDuser: Flow<String> = dataStore.data.map {
        it[IDUSER] ?: ""
    }

    val image: Flow<String> = dataStore.data.map {
        it[IMAGE] ?: ""
    }

    val name: Flow<String> = dataStore.data.map {
        it[NAME] ?: ""
    }

    val password: Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val dateOfBirth: Flow<String> = dataStore.data.map {
        it[DATE_OF_BIRTH] ?: ""
    }

    val username: Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val boolean: Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }

    val imageProfile: Flow<String> = dataStore.data.map {
        it[IMAGEPROFILE] ?: ""
    }

    suspend fun clearDataLogin() {
        dataStore.edit {
            it.clear()
        }
    }
}