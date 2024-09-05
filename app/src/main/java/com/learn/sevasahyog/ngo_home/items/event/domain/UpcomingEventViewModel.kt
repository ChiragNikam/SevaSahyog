package com.learn.sevasahyog.ngo_home.items.event.domain

import android.util.Log
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

    private val _pastYearEvents = MutableStateFlow(ArrayList<Int>())
    val pastEventYears get() = _pastYearEvents.asStateFlow()

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

                if (response.isSuccessful) {
                    _upcomingEvents.value = response.body() ?: emptyList()

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

    fun loadPastEventYears(){
        viewModelScope.launch {
            try {
                val response = ngoService.getPastEventYears("Bearer ${accessToken.value}",userId.value)

                if (response.isSuccessful){
                    _pastYearEvents.value = (response.body() ?: emptyList()) as ArrayList<Int>
                    Log.d("event_years", pastEventYears.value.toString())
                }
            } catch (e: Exception){
                e.localizedMessage?.let { Log.e("error_fetching_years", it) }
            }
        }
    }
}