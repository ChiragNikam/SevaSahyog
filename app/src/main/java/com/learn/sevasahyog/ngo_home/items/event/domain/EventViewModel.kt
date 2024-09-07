package com.learn.sevasahyog.ngo_home.items.event.domain

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.learn.sevasahyog.common.BASE_URL
import com.learn.sevasahyog.data.ErrorResponse
import com.learn.sevasahyog.network.RetrofitInstance
import com.learn.sevasahyog.ngo_home.data.NgoService
import com.learn.sevasahyog.ngo_home.items.event.data.CreateEvent
import com.learn.sevasahyog.ngo_home.items.event.data.Event
import com.learn.sevasahyog.ngo_home.items.event.data.EventRequest
import com.learn.sevasahyog.ngo_home.items.event.data.EventResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.ArrayList

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

    private val _eventList = MutableStateFlow(listOf<Event>())
    val eventList get() = _eventList.asStateFlow()

    private val _eventsByYearList = MutableStateFlow<List<Event>>(emptyList())
    val eventsByYearList get() = _eventsByYearList.asStateFlow()

    private val _userId = MutableStateFlow("")
    val userId get() = _userId.asStateFlow()

    fun updateUserId(userId: String) {
        _userId.value = userId
    }

    // for main events
    suspend fun loadEventByUser(eventYear: Int) {
        viewModelScope.launch {
            try {

                val eventListResponse =
                    ngoService.getEventListByUser("Bearer ${accessToken.value}", userId.value)

                if (eventListResponse.isSuccessful) {

                    _eventList.value = eventListResponse.body()!!

                    // filter those events by year
                    _eventsByYearList.value = filterEventsByYear(eventYear)

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

    fun filterEventsByYear(year: Int): List<Event> {

        val filteredEvent = mutableListOf<Event>()
        for (event in eventList.value) {
            if (year == event.yyyy) {
                filteredEvent.add(event)
            }
        }

        return filteredEvent
    }

    // for upcoming events
    private val _upcomingEvents = MutableStateFlow<List<Event>>(emptyList())
    val upcomingEvents: StateFlow<List<Event>> = _upcomingEvents

    private val _pastYearEvents = MutableStateFlow(ArrayList<Int>())
    val pastEventYears get() = _pastYearEvents.asStateFlow()

    private val _selectedEventYear = MutableStateFlow(0)
    val selectedEventYear get() = _selectedEventYear.asStateFlow()

    fun updateSelectedEventYear(year: Int) {
        Log.d("year_updated", "year: $year")
        _selectedEventYear.value = year
    }

    // Function to load upcoming events
    fun loadUpcomingEvents() {
        viewModelScope.launch {
            try {
                val response =
                    ngoService.getUpcomingEvents("Bearer ${accessToken.value}", userId.value)

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

    fun loadPastEventYears() {
        viewModelScope.launch {
            try {
                val response =
                    ngoService.getPastEventYears("Bearer ${accessToken.value}", userId.value)

                if (response.isSuccessful) {
                    _pastYearEvents.value = (response.body() ?: emptyList()) as ArrayList<Int>
                    Log.d("event_years", pastEventYears.value.toString())
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("error_fetching_years", it) }
            }
        }
    }

    private val _eventResponse = MutableStateFlow(EventResponse())
    val eventResponse get() = _eventResponse.asStateFlow()

    fun getEventByItsId(eventId: Long) {
        viewModelScope.launch {
            try {
                val response = ngoService.getEventByItsId("Bearer ${accessToken.value}", userId = userId.value, eventId = eventId)
                if (response.isSuccessful){
                    _eventResponse.value = response.body()!!
                } else {
                    Log.e("event_response_fail", "failed to get response")
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("error_fetching_years", it) }
            }
        }
    }
}

