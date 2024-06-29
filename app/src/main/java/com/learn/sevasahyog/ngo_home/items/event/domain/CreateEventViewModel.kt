package com.learn.sevasahyog.ngo_home.items.event.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.learn.sevasahyog.auth.data.dataclass.ErrorResponse
import com.learn.sevasahyog.network.RetrofitInstance
import com.learn.sevasahyog.ngo_home.data.NgoAccount
import com.learn.sevasahyog.ngo_home.data.NgoService
import com.learn.sevasahyog.ngo_home.items.event.data.CreateEvent
import com.learn.sevasahyog.ngo_home.items.event.data.EventRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.create

class EventViewModel : ViewModel() {

    // access token
    private val _accessToken = MutableStateFlow("")
    val accessToken get() = _accessToken

    fun updateAccessToken(token: String) {
        _accessToken.value = token
    }

    // email
    private val _email = MutableStateFlow("")
    val email get() = _email

    fun updateEmail(email: String) {
        _email.value = email
    }

    private val _event = MutableStateFlow(EventRequest())
    val event get() = _event.asStateFlow()

    fun updateEvent(update: (EventRequest) -> EventRequest) {
        _event.value = update(_event.value)
    }

    private val ngoService = RetrofitInstance.getClient("https://sevasahyogapi.azurewebsites.net/")
        .create(NgoService::class.java)

    // Function to create event
     fun createEvent() {
        viewModelScope.launch {
            try {
                val createEvent =CreateEvent(_email.value,_event.value)
                val eventResponse = ngoService.createEvent(
                    token = "Bearer ${_accessToken.value}",
                    createEventBody = createEvent
                )

                if (eventResponse.isSuccessful) {
                    val savedEvent =eventResponse.body()
                    Log.d("create_event","event response $savedEvent")

                } else {
                    val errorBody = eventResponse.errorBody()?.string()
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

                    Log.d("error_response", eventResponse.code().toString())
                    Log.d("error_response_body", errorResponse?.errorMessage ?: "No error message")
                }
            } catch (e: Exception) {
                Log.e("NetworkError", "Error creating event", e)
            }
        }
    }
}

