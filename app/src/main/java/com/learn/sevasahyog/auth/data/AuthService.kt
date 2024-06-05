package com.learn.sevasahyog.auth.data

import com.learn.sevasahyog.auth.data.dataclass.SignInRequest
import com.learn.sevasahyog.auth.data.dataclass.SignInResponseNgo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/signIn")
    fun signInAsNgo(@Body request: SignInRequest): Call<SignInResponseNgo>
}