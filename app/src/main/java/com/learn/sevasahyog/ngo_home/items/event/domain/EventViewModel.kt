package com.learn.sevasahyog.ngo_home.items.event.domain

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.learn.sevasahyog.common.BASE_URL
import com.learn.sevasahyog.data.ErrorResponse
import com.learn.sevasahyog.network.RetrofitInstance
import com.learn.sevasahyog.ngo_home.data.NgoService
import com.learn.sevasahyog.ngo_home.items.event.data.Event
import com.learn.sevasahyog.ngo_home.items.event.data.EventResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    // internet connection
    private val _internetConnection = MutableStateFlow(true)
    val internetConnection get() = _internetConnection.asStateFlow()

    // are events available
    private val _upcomingEventShimmerEffect = MutableStateFlow(true)
    val upcomingEventShimmerEffect get() = _upcomingEventShimmerEffect.asStateFlow()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        _internetConnection.value = false
        throwable.localizedMessage?.let { Log.e("error", it) }
    }

    fun updateUserId(userId: String) {
        _userId.value = userId
    }

    private val _eventImagesUrls = MutableStateFlow<List<String>>(listOf())
    val eventImagesUrls get() = _eventImagesUrls.asStateFlow()

    fun updateEventImagesUrls(urls: List<String>) {
        this._eventImagesUrls.value = urls
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

    // uploading event images to firebase and getting download link of images
    suspend fun uploadImagesToFirebase(
        uris: List<Uri>,
        eventId: String,
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            val storageReference = FirebaseStorage.getInstance().reference
            val uploadedImageUrls = mutableListOf<String>()

            try {
                uris.forEach { uri ->
                    // Create a unique filename
                    val fileName = "${System.currentTimeMillis()}.jpg"
                    val fileRef: StorageReference = storageReference.child("${userId.value}/Events/$eventId/$fileName")
                    // Upload the file and get the download URL
                    fileRef.putFile(uri).await()
                    val downloadUrl = fileRef.downloadUrl.await().toString()

                    // Add the download URL to the list
                    uploadedImageUrls.add(downloadUrl)
                }

                // Return the list of uploaded image URLs
                onSuccess(uploadedImageUrls)
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    // set download link of event images to event
    fun updateEventImagesUrlByAPI(eventImages: List<String>, eventId: Long) {
        viewModelScope.launch {
            val eventImagesResponse = ngoService.updateEventImages(
                token = "Bearer ${accessToken.value}",
                eventId = eventId,
                eventImagesUrls = eventImages
            )

            if(eventImagesResponse.isSuccessful){
                val updatedEvent = eventImagesResponse.body()
                if (updatedEvent != null) {
                    _eventResponse.value.eventImagesUrls = updatedEvent.eventImagesUrls
                    updatedEvent.eventImagesUrls?.let { updateEventImagesUrls(it) }
                }
            } else {
                throw Exception("API call to upload image failed, due to ${eventImagesResponse.code()}, ${eventImagesResponse.errorBody()}")
            }
        }
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
                    _upcomingEventShimmerEffect.value = false
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
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("error_fetching_years", it) }
            }
        }
    }


}

