package com.learn.sevasahyog.auth.repo

import com.learn.sevasahyog.auth.data.AuthService
import com.learn.sevasahyog.auth.data.dataclass.SignInRequest
import com.learn.sevasahyog.auth.data.dataclass.SignInResponseNgo
import com.learn.sevasahyog.network.RetrofitInstance
import retrofit2.Callback
import retrofit2.Response

class AuthRepo {
    val authService = RetrofitInstance.getClient("http://192.168.1.14:8080").create(AuthService::class.java)

    fun ngoSignIn(
        signInData: SignInRequest,
        onResponse: (call: retrofit2.Call<SignInResponseNgo>, response: Response<SignInResponseNgo>) -> Unit,
        onFailure: (call: retrofit2.Call<SignInResponseNgo>, t: Throwable) -> Unit
    ) {
        authService.signInAsNgo(signInData).enqueue(object : Callback<SignInResponseNgo> {
            override fun onResponse(
                call: retrofit2.Call<SignInResponseNgo>,
                response: Response<SignInResponseNgo>
            ) {
                onResponse(call, response)
            }

            override fun onFailure(call: retrofit2.Call<SignInResponseNgo>, t: Throwable) {
                onFailure(call, t)
            }

        })

    }
}