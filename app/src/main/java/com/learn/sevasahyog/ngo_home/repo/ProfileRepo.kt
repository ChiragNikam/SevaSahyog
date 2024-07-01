package com.learn.sevasahyog.ngo_home.repo

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.learn.sevasahyog.data.ErrorResponse
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

            if (response.isSuccessful) {
                Log.d("API_CALL", "Success: ${response.body()}")
                response.body()
            } else {
                Log.e("API_CALL", "Failed: ${response.code()} ${response.errorBody()?.string()}")
                ErrorResponse(response.code(), response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            Log.e("API_CALL", "Exception: ${e.message}")
            ErrorResponse(404, "Exception occurred: ${e.message}")
        }
    }
}