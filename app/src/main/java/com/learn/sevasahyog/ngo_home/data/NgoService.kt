package com.learn.sevasahyog.ngo_home.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NgoService {
    @GET("/account/ngo/{id}")
    suspend fun getUserProfile(@Header("Authorization") token: String, id: String): Response<NgoAccount>
}