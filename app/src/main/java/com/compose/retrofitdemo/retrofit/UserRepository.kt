package com.compose.retrofitdemo.retrofit

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import javax.inject.Inject
//---retrofit using class-------
class UserRepository @Inject constructor(private val api : RetrofitAPI,@ApplicationContext private val context : Context) {
    suspend fun postUserData(data : DataModel) : DataModel?{
        return try {
            api.postData(data)
        }catch (e: Exception) {
            Log.e("API_ERROR", "Error: ${e.message}")  // ✅ Logs errors in Logcat
            null
        }
    }

    //----volley Request------
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun postUserData1(data : DataModel) : String? {
        return suspendCancellableCoroutine {continuation ->
            val url = "https://reqres.in/api/users"
            val jsonRequest = JSONObject()
            jsonRequest.put("name", data.username)
            jsonRequest.put("job", data.job)

            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonRequest,
                { response ->
                    Log.d("API_RESPONSE", "Success: $response")
                    continuation.resume(
                        "User: ${response.optString("name")}, Job: ${
                            response.optString(
                                "job"
                            )
                        }"
                    ) {

                    }
                },
                { error ->
                    Log.e("API_ERROR", "Error: ${error.message}")
                    continuation.resume("API Error: ${error.message}") {}
                })

            requestQueue.add(request)
        }
    }
}
