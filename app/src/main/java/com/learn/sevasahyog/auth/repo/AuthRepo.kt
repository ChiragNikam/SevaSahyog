package com.learn.sevasahyog.auth.repo

import androidx.lifecycle.LifecycleCoroutineScope
import com.learn.sevasahyog.auth.data.AuthService
import com.learn.sevasahyog.auth.data.dataclass.ErrorResponse
import com.learn.sevasahyog.auth.data.dataclass.NgoAccount
import com.learn.sevasahyog.auth.data.dataclass.SignInRequest
import com.learn.sevasahyog.auth.data.dataclass.SignInResponseNgo
import com.learn.sevasahyog.auth.data.dataclass.SignUpRequestNgo
import com.learn.sevasahyog.common.BASE_URL
import com.learn.sevasahyog.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepo {
    private val authService =
        RetrofitInstance.getClient(BASE_URL).create(AuthService::class.java)

    suspend fun ngoSignIn(
        signInData: SignInRequest,
        onResponse: (call: Call<SignInResponseNgo>, response: Response<SignInResponseNgo>) -> Unit,
        onFailure: (call: Call<SignInResponseNgo>, t: Throwable) -> Unit
    ) {
        authService.signInAsNgo(signInData).enqueue(object : Callback<SignInResponseNgo> {
            override fun onResponse(
                call: Call<SignInResponseNgo>,
                response: Response<SignInResponseNgo>
            ) {
                onResponse(call, response)
            }

            override fun onFailure(call: Call<SignInResponseNgo>, t: Throwable) {
                onFailure(call, t)
            }

        })

    }

    fun ngoSignUp(
        signUpData: SignUpRequestNgo,
        onResponse: (call: Call<NgoAccount>, response: Response<NgoAccount>) -> Unit,
        onFailure: (call: Call<NgoAccount>, t: Throwable) -> Unit
    ) {
        authService.singUpAsNgo(signUpData).enqueue(object : Callback<NgoAccount> {
            override fun onResponse(call: Call<NgoAccount>, response: Response<NgoAccount>) {
                onResponse(call, response)
            }

            override fun onFailure(call: Call<NgoAccount>, t: Throwable) {
                onFailure(call, t)
            }
        })
    }
}