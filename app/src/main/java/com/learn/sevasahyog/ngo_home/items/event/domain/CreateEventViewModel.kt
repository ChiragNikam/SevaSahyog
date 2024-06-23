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

    //eventName
    private val _eventName = MutableStateFlow("")
    val eventName get() = _eventName.asStateFlow()
    fun updateEventName(eventName: String) {
        this._event.value.name = eventName
    }

    //eventDate
    private val _eventDate = MutableStateFlow("")
    val eventDate get() = _eventDate.asStateFlow()
    fun updateEventDate(eventDate: String) {
        this._eventDate.value = eventDate
    }

    //eventLocation
    private val _eventLocation = MutableStateFlow("")
    val eventLocation get() = _eventLocation.asStateFlow()
    fun updateEventLocation(eventLocation: String) {
        this._event.value.location = eventLocation
    }

    //event organizer name
    private val _eventOrganizerName = MutableStateFlow("")
    val eventOrganizerName get() = _eventOrganizerName.asStateFlow()
    fun updateEventOrganizerName(eventOrganizerName: String) {
        this._event.value.organizer = eventOrganizerName
    }

    //eventDate
    private val _organizerMobileNo = MutableStateFlow("")
    val organizerMobileNo get() = _organizerMobileNo.asStateFlow()
//    fun updateOrganizerMobileNo(organizerMobileNo: String) {
//        this._event.value.organizerMobileNumber = organizerMobileNo
//    }

    //eventDate
    private val _eventShortDesc = MutableStateFlow("")
    val eventShortDesc get() = _eventShortDesc.asStateFlow()
    fun updateEventShortDesc(eventShortDesc: String) {
        this._event.value.shortDesc = eventShortDesc
    }

    //eventDate
    private val _eventLongDesc = MutableStateFlow("")
    val eventLongDesc get() = _eventLongDesc.asStateFlow()
    fun updateEventLongDesc(eventLongDesc: String) {
        this._event.value.longDesc = eventLongDesc
    }

    // access token
    private val _accessToken = MutableStateFlow("")
    val accessToken get() = _accessToken

    fun updateAccessToken(token: String){
        _accessToken.value = token
    }
    private val _createEvent = MutableStateFlow(CreateEvent())
    val createEvent get() = _createEvent

    private val _event = MutableStateFlow(EventRequest())
    val event get() = _event


    // Function to create event
    fun createEvent() {
        viewModelScope.launch {
            try {
                val ngoService = RetrofitInstance.getClient("http://192.168.43.231:8080/").create(NgoService::class.java)
                val eventResponse = ngoService.createEvent(token = "Bearer ${_accessToken.value}", createEventBody = _createEvent.value)

                if (eventResponse.isSuccessful) {
                    _event.value = (eventResponse.body() ?: EventRequest()) as EventRequest
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

