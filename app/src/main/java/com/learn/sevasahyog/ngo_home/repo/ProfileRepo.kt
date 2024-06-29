package com.learn.sevasahyog.ngo_home.repo

import com.learn.sevasahyog.auth.data.dataclass.ErrorResponse
import com.learn.sevasahyog.common.BASE_URL
import com.learn.sevasahyog.network.RetrofitInstance
import com.learn.sevasahyog.ngo_home.data.NgoService
import com.learn.sevasahyog.ngo_home.data.UpdatePicsRequest

class ProfileRepo {
    private val ngoService =
        RetrofitInstance.getClient(BASE_URL).create(NgoService::class.java)

    suspend fun updateProfileBackgroundPic(
        token: String,
        userId: String,
        updatePicsRequest: UpdatePicsRequest
    ): Any? {
        return try {
            val response = ngoService.updateProfileBackgroundPic(token, userId, updatePicsRequest)
            if (response.isSuccessful)
                response.body()
            else
                ErrorResponse(response.code(), response.body().toString())
        } catch (e: Exception){
            ErrorResponse(404, e.message.toString())
        }
    }
}