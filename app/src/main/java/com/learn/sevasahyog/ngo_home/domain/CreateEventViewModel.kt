package com.learn.sevasahyog.ngo_home.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateEventViewModel:ViewModel() {

    //eventName
    private val _eventName=MutableStateFlow("")
    val eventName get() = _eventName.asStateFlow()
    fun updateEventName(eventName:String){
        this._eventName.value=eventName
    }

    //eventDate
    private val _eventDate=MutableStateFlow("")
    val eventDate get() = _eventDate.asStateFlow()
    fun updateEventDate(eventDate:String){
        this._eventDate.value=eventDate
    }

    //eventLocation
    private val _eventLocation=MutableStateFlow("")
    val eventLocation get() = _eventLocation.asStateFlow()
    fun updateEventLocation(eventLocation:String){
        this._eventLocation.value=eventLocation
    }

    //event organizer name
    private val _eventOrganizerName=MutableStateFlow("")
    val eventOrganizerName get() = _eventOrganizerName.asStateFlow()
    fun updateEventOrganizerName(eventOrganizerName:String){
        this._eventOrganizerName.value=eventOrganizerName
    }

    //eventDate
    private val _organizerMobileNo=MutableStateFlow("")
    val organizerMobileNo get() = _organizerMobileNo.asStateFlow()
    fun updateOrganizerMobileNo(organizerMobileNo:String){
        this._organizerMobileNo.value=organizerMobileNo
    }

    //eventDate
    private val _eventShortDesc=MutableStateFlow("")
    val eventShortDesc get() = _eventShortDesc.asStateFlow()
    fun updateEventShortDesc(eventShortDesc:String){
        this._eventShortDesc.value=eventShortDesc
    }

    //eventDate
    private val _eventLongDesc=MutableStateFlow("")
    val eventLongDesc get() = _eventLongDesc.asStateFlow()
    fun updateEventLongDesc(eventLongDesc:String){
        this._eventLongDesc.value=eventLongDesc
    }




}