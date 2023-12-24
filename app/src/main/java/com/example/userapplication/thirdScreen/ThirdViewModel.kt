package com.example.userapplication.thirdScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.response.DataItem
import com.example.data.response.ListUserResponse
import com.example.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdViewModel : ViewModel() {
    private val isLoading = MutableLiveData<Boolean>()
    private val _listUsers = MutableLiveData<List<DataItem>>()
    val listUsers: MutableLiveData<List<DataItem>> = _listUsers
    private var currentPage = 1

    fun getAllUsers() {
        isLoading.value = true
        val client = ApiConfig.getApiService().getUser(page = currentPage, perPage = 10)

        client.enqueue(object : Callback<ListUserResponse> {
            override fun onResponse(
                call: Call<ListUserResponse>,
                response: Response<ListUserResponse>
            ) {
                if (response.isSuccessful) {
                    val newData = response.body()?.data
                    if (currentPage == 1) {
                        _listUsers.postValue(newData ?: emptyList())
                    }else {
                        val currentData = _listUsers.value.orEmpty()
                        newData?.let {
                            _listUsers.postValue(currentData + it)
                        }
                    }
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                Log.d("ScreenViewModel", "OnFailure: ${t.message}")
                isLoading.value = false
            }
        })
    }

    fun refreshData() {
        currentPage = 1
        getAllUsers()
    }

    fun loadNextPage() {
        currentPage++
        getAllUsers()
    }
}
