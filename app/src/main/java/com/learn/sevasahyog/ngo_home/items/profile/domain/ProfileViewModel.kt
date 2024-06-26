package com.learn.sevasahyog.ngo_home.items.profile.domain

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.learn.sevasahyog.auth.data.dataclass.ErrorResponse
import com.learn.sevasahyog.network.RetrofitInstance
import com.learn.sevasahyog.ngo_home.data.NgoAccount
import com.learn.sevasahyog.ngo_home.data.NgoService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.Dispatcher

class ProfileViewModel : ViewModel() {

    //userInfo data
    //cover image
    private val _coverImage = MutableStateFlow("")
    val coverImage get() = _coverImage.asStateFlow()

    fun updateCoverImage(coverImage: String) {
        this._coverImage.value = coverImage
    }

    private val _profileImage = MutableStateFlow("")
    val profileImage get() = _profileImage.asStateFlow()

    fun updateProfileImage(profileImage: String) {
        this._profileImage.value = profileImage
    }

    // access token
    private val _accessToken = MutableStateFlow("")
    val accessToken get() = _accessToken

    fun updateAccessToken(token: String){
        _accessToken.value = token
    }

    // user id
    private val _userId = MutableStateFlow("")
    val userId get() = _userId.asStateFlow()

    fun updateUserId(uid: String){
        _userId.value = uid
    }

    // user-profile progress
    private val _userProfileProgress = MutableStateFlow(true)
    val userProfileProgress get() = _userProfileProgress

    // ngo profile obj
    private val _profile = MutableStateFlow(NgoAccount())
    val profile get() = _profile.asStateFlow()

    // internet connection
    private val _internetConnection = MutableStateFlow(true)
    val internetConnection get() = _internetConnection.asStateFlow()

    private val handler = CoroutineExceptionHandler{ _, throwable ->
//        if (throwable is )
        _internetConnection.value = false
        throwable.localizedMessage?.let { Log.e("error", it) }
    }
    private val ngoService: NgoService = RetrofitInstance.getClient("https://sevasahyogapi.azurewebsites.net/").create(NgoService::class.java)


    fun loadProfile(){
        viewModelScope.launch(handler) {
            // if internet available
            _internetConnection.value = true

            val userProfileResponse = ngoService.getUserProfile(token = "Bearer ${_accessToken.value}", _userId.value)

            if (userProfileResponse.isSuccessful) {
                _profile.value = userProfileResponse.body()!!
                _userProfileProgress.value = false
            } else{
                val errorBody = userProfileResponse.errorBody()?.string()
                Log.d("error_response_raw", errorBody ?: "No error body")

                val gson = GsonBuilder().setLenient().create()
                val errorResponse: ErrorResponse? = errorBody?.let {
                    try {
                        gson.fromJson(it, ErrorResponse::class.java)
                    } catch (e: JsonSyntaxException) {
                        Log.e("GsonError", "Error parsing JSON", e)
                        null
                    }
                }

                Log.d("error_response", userProfileResponse.code().toString())
                _userProfileProgress.value = false
            }
        }
    }

    //function to upload image to firebase
    fun uploadImageToFirebase(uri: Uri, fileName:String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit){
        viewModelScope.launch {
            try {
                val storageReference =FirebaseStorage.getInstance().reference
                val fileRef = storageReference.child("profile_images/$fileName")
                fileRef.putFile(uri).await()
                val downloadUrl = fileRef.downloadUrl.await().toString()
                onSuccess(downloadUrl)
            } catch (e:Exception){
                onFailure(e)
            }
        }

    }
}


data class ErrorResponse(val error: String){

}




