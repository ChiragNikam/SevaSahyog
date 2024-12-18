package com.learn.sevasahyog.ngo_home.data

import com.learn.sevasahyog.ngo_home.items.event.data.CreateEvent
import com.learn.sevasahyog.ngo_home.items.event.data.Event
import com.learn.sevasahyog.ngo_home.items.event.data.EventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NgoService {
    @GET("/account/ngo/{id}")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<NgoAccount>

    @PUT("/account/ngo/updateImage/{userId}")
    suspend fun updateProfileBackgroundPic(
        @Header("Authorization") token: String,
        @Path("userId") id: String,
        @Body updatePicsRequest: UpdatePicsRequest
    ): Response<NgoAccount>

    // Event Endpoints

    @POST("ngo/events")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Body createEventBody: CreateEvent
    ): Response<EventResponse>

    @PUT("ngo/events/{eventId}/eventImages")
    suspend fun updateEventImages(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Long,
        @Body eventImagesUrls: List<String>
    ): Response<EventResponse>

    @GET("ngo/events/user/{id}/upcoming")
    suspend fun getUpcomingEvents(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Response<List<Event>>

    @GET("ngo/events/user/{id}/pastEventYears")
    suspend fun getPastEventYears(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<List<Int>>

    @GET("/ngo/events/user/{id}")
    suspend fun getEventListByUser(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<List<Event>>

    @GET("/ngo/events/user/{userId}/{eventId}")
    suspend fun getEventByItsId(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Path("eventId") eventId: Long
    ): Response<EventResponse>
}