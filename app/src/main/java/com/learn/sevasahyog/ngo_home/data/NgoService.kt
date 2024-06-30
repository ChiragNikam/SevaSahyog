package com.learn.sevasahyog.ngo_home.data

import com.learn.sevasahyog.ngo_home.items.event.data.CreateEvent
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

    @PUT("/account/ngo/updateImage/{id}")
    suspend fun updateProfileBackgroundPic(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updatePicsRequest: UpdatePicsRequest
    ): Response<Any>

    @POST("ngo/events")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Body createEventBody: CreateEvent
    ): Response<EventResponse>
}