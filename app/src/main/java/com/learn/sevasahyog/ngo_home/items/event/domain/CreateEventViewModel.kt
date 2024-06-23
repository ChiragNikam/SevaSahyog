package com.learn.sevasahyog.ngo_home.items.event.domain

import androidx.lifecycle.ViewModel
import com.learn.sevasahyog.ngo_home.data.NgoAccount
import com.learn.sevasahyog.ngo_home.items.event.data.CreateEvent
import com.learn.sevasahyog.ngo_home.items.event.data.EventRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    private val _createEvent = MutableStateFlow(CreateEvent())
    val createEvent get() = _createEvent

    private val _event = MutableStateFlow(EventRequest())
    val event get() = _event

}