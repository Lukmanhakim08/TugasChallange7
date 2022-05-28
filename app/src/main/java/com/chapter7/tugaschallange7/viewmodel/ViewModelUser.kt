package com.chapter7.tugaschallange7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapter7.tugaschallange7.model.DataUserResponseItem
import com.chapter7.tugaschallange7.model.PostNewUser
import com.chapter7.tugaschallange7.model.RequesUser
import com.chapter7.tugaschallange7.network.ApiUserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelUser @Inject constructor(api: ApiUserService) : ViewModel() {
    private val liveDataUser = MutableLiveData<List<DataUserResponseItem>>()
    val user: LiveData<List<DataUserResponseItem>> = liveDataUser
    private val apiService = api

    init {
        viewModelScope.launch {
            val datauser = api.getAllUser()
            delay(2000)
            liveDataUser.value = datauser
        }
    }

    fun insertNewUSer(requesUser : RequesUser): Boolean{
        var messageResponse = false
        viewModelScope.launch {
            apiService.addDataUser(requesUser)
                .enqueue(object : Callback<PostNewUser> {
                    override fun onResponse(
                        call: Call<PostNewUser>,
                        response: Response<PostNewUser>
                    ) {
                        messageResponse = response.isSuccessful
                    }

                    override fun onFailure(call: Call<PostNewUser>, t: Throwable) {
                        messageResponse = false
                    }

                })
        }
        return messageResponse
    }

    fun updateDataUser(id: String, requesUser: RequesUser): Boolean{
        var messageResponse = false
        viewModelScope.launch {
            apiService.updateDataUser(id, requesUser)
                .enqueue(object : Callback<List<DataUserResponseItem>> {
                    override fun onResponse(
                        call: Call<List<DataUserResponseItem>>,
                        response: Response<List<DataUserResponseItem>>
                    ) {
                        messageResponse = response.isSuccessful
                    }

                    override fun onFailure(call: Call<List<DataUserResponseItem>>, t: Throwable) {
                        messageResponse = false
                    }

                })
        }
        return messageResponse
    }
}