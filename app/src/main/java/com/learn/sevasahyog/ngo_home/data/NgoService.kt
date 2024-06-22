package com.learn.sevasahyog.ngo_home.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface NgoService {
    @GET("/account/ngo/{id}")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<NgoAccount>
}