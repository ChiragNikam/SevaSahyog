package com.learn.sevasahyog.ngo_home.items.event.domain

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.sevasahyog.common.BASE_URL
import com.learn.sevasahyog.network.RetrofitInstance
import com.learn.sevasahyog.ngo_home.data.NgoService
import com.learn.sevasahyog.ngo_home.items.event.data.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class UpcomingEventViewModel: ViewModel() {



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

    private val _userId= MutableStateFlow("")
    val userId get() = _userId.asStateFlow()

    fun updateUserId(userId:String){
        _userId.value=userId
    }

    private val _upcomingEvents = MutableStateFlow<List<Event>>(emptyList())
    val upcomingEvents: StateFlow<List<Event>> = _upcomingEvents

    // Function to load upcoming events
    fun loadUpcomingEvents() {
        viewModelScope.launch {
            try {
                val response = ngoService.getUpcomingEvents("Bearer ${accessToken.value}",userId.value)

                // Log the raw response
                Log.d("UpcomingEventViewModel1", "Response: ${response.raw()}")

                if (response.isSuccessful) {
                    _upcomingEvents.value = response.body() ?: emptyList()

                    // Log the list of upcoming events
                    Log.d("UpcomingEventViewModel2", "Upcoming events: ${_upcomingEvents.value}")
                } else {
                    val errorMessage = "Error: ${response.code()} ${response.message()}"
                    _error.value = errorMessage

                    // Log the error response
                    Log.e("UpcomingEventViewModel3", errorMessage)
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage

                // Log the exception message
                Log.e("UpcomingEventViewModel4", "Exception: ${e.localizedMessage}", e)
            }
        }


    }


    private val _eventYears = MutableStateFlow<Set<Int>>(emptySet())
    val eventYears: StateFlow<Set<Int>> get() = _eventYears

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadEventYears() {
        viewModelScope.launch {
            try {
                val token = "Bearer ${accessToken.value}"
                val userId = userId.value

                // Log the token and userId
                Log.d("pastEventsDebug", "Token: $token")
                Log.d("pastEventsDebug", "UserId: $userId")

                val response = ngoService.getPastEventYears(token, userId)
                Log.d("pastEvents1", "Response: ${response.raw()}")

                if (response.isSuccessful) {
                   // _eventYears.value = response.body()!!
                    Log.d("pastEvents2", "Upcoming events: ${_eventYears.value}")
                } else {
                    val errorMessage = "Error: ${response.code()} ${response.message()}"
                    _error.value = errorMessage
                    Log.e("pastEvents3", errorMessage)
                }
            } catch (e: HttpException) {
                _error.value = "Network error: ${e.message}"
            } catch (e: IOException) {
                _error.value = "IO error: ${e.message}"
            } catch (e: Exception) {
                _error.value = "Unexpected error: ${e.message}"
                Log.e("pastEvents4", "Exception: ${e.localizedMessage}", e)
            }
        }
    }

}