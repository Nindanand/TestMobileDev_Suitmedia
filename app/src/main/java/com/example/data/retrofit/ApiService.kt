package com.example.data.retrofit

import com.example.data.response.ListUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<ListUserResponse>
}