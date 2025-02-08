package com.compose.retrofitdemo.retrofit

import android.telecom.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("users")
    suspend fun postData(@Body dataModel: DataModel): DataModel
}