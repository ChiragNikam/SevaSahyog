package com.learn.sevasahyog.ngo_home.items.event.domain

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.learn.sevasahyog.common.BASE_URL
import com.learn.sevasahyog.data.ErrorResponse
import com.learn.sevasahyog.network.RetrofitInstance
import com.learn.sevasahyog.ngo_home.data.NgoService
import com.learn.sevasahyog.ngo_home.items.event.data.CreateEvent
import com.learn.sevasahyog.ngo_home.items.event.data.Event
import com.learn.sevasahyog.ngo_home.items.event.data.EventRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

// access token
private val _accessToken = MutableStateFlow("")
val accessToken get() = _accessToken

fun updateAccessToken(token: String) {
    _accessToken.value = token
}

private val ngoService = RetrofitInstance.getClient(BASE_URL)
    .create(NgoService::class.java)


private val _error = MutableStateFlow<String?>(null)
val error: StateFlow<String?> get() = _error.asStateFlow()

    private val _eventList =MutableStateFlow(listOf<Event>())
    val  eventList get() = _eventList.asStateFlow()

    private val _userId= MutableStateFlow("")
    val userId get() = _userId.asStateFlow()

    fun updateUserId(userId:String){
        _userId.value=userId
    }
    @SuppressLint("SuspiciousIndentation")
    fun loadEventByUser() {
        viewModelScope.launch {
            try {

            val eventListResponse=ngoService.getEventListByUser(accessToken.value, userId.value)

                Log.d("eventByUser","event by user  ${_eventList.value}")
                if (eventListResponse.isSuccessful) {
                     _eventList.value= eventListResponse.body()!!
                    Log.d("eventsByUser" ,_eventList.value.toString())

                } else {
                    val errorBody = eventListResponse.errorBody()?.string()
                    Log.d("error_response_raw", errorBody ?: "No error body")

                    val gson = GsonBuilder().setLenient().create()
                    val errorResponse: ErrorResponse? = errorBody?.let {
                        try {
                            gson.fromJson(it, ErrorResponse::class.java)
                        } catch (e: JsonSyntaxException) {
                            Log.e("GsonError", "Error parsing JSON", e)
                            null
                        }
                    }

                    Log.d("error_response_body", errorResponse?.errorMessage ?: "No error message")
                }
            } catch (e: Exception) {
                Log.e("NetworkError", "Error creating event", e)
            }
        }
    }
}

