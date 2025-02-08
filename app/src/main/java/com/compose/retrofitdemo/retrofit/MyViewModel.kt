package com.compose.retrofitdemo.retrofit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    private val _response = MutableStateFlow("Response will appear here")
    val response: StateFlow<String> = _response

    //--------retrofit post request---------
    fun sendUserData(username: String, job: String) {
        viewModelScope.launch {
            try {
                val requestData = DataModel(username, job)
                val responseData = repository.postUserData(requestData)
                if (responseData != null) {
                    _response.value = "User: ${responseData.username}, Job: ${responseData.job}"
                } else {
                    _response.value = "API Error: Response was null!"
                }

            } catch (e: Exception) {
                _response.value = "Error ${e.message}"
                Log.e("API_ERROR", "Exception: ${e.message}")
            }
        }
    }

    //--------volley post request------
    fun sendUserData1(username: String, job: String) {
        viewModelScope.launch {
            val requestData = DataModel(username, job)
            _response.value = "Sending..."
            val result = repository.postUserData1(requestData)
            if (result != null) {
                _response.value = result
            }
        }
    }
}
