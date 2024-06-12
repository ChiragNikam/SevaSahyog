package com.learn.sevasahyog.auth.data

import com.learn.sevasahyog.auth.data.dataclass.NgoAccount
import com.learn.sevasahyog.auth.data.dataclass.SignInRequest
import com.learn.sevasahyog.auth.data.dataclass.SignInResponseNgo
import com.learn.sevasahyog.auth.data.dataclass.SignUpRequestNgo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/ngo/signIn")
    fun signInAsNgo(@Body request: SignInRequest): Call<SignInResponseNgo>

    @POST("/auth/ngo/signUp")
    fun singUpAsNgo(@Body request: SignUpRequestNgo): Call<NgoAccount>
}