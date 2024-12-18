package com.learn.sevasahyog.ngo_home.items.event.data

data class EventRequest(
    var name: String = "",
    var longDesc: String = "",
    var shortDesc: String = "",
    var organizer: String = "",
    var organizerPhone: String = "",
    var location: String = "",
    var dd: Int = 0,
    var mm: Int = 0,
    var yyyy: Int = 0,
    var status: Int = 2
)

data class EventResponse(
    var eventId: Int = 0,
    var name: String = "",
    var longDesc: String = "",
    var shortDesc: String = "",
    var organizer: String = "",
    var organizerPhone: String = "",
    var location: String = "",
    var dd: Int = 0,
    var mm: Int = 0,
    var yyyy: Int = 0,
    var status: Int = 0,
    var eventImagesUrls: List<String>? = listOf()
)

data class CreateEvent(
    var email: String = "",
    var event: EventRequest = EventRequest()
)
