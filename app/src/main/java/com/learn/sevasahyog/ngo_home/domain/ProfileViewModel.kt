package com.learn.sevasahyog.ngo_home.domain

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel: ViewModel() {
    //userInfo data
    //cover image
    private val _coverImage=MutableStateFlow("")
    val coverImage get() = _coverImage.asStateFlow()

    fun updateCoverImage(coverImage:String){
        this._coverImage.value=coverImage
    }

    //user Profile
    private val _userProfile=MutableStateFlow("")
    val userProfile get() = _userProfile.asStateFlow()

    fun updateUserProfile(userProfile:String){
        this._userProfile.value=userProfile
    }

    //username
    private val _userName=MutableStateFlow("")
    val userName get() = _userName.asStateFlow()

    fun updateUserName(userName:String){
      this._userName.value=userName
    }

    //phone number
    private val _phoneNumber=MutableStateFlow("")
    val phoneNumber get() = _phoneNumber.asStateFlow()

    fun updatePhoneNumber(phoneNumber:String){
        this._phoneNumber.value=phoneNumber
    }

    //email
    private val _email=MutableStateFlow("")
    val email get() = _email.asStateFlow()

    fun updateEmailName(email:String){
        this._email.value=email
    }

    //Ngo Info
    //ngoname
    private val _ngoName=MutableStateFlow("")
    val ngoName get() = _ngoName.asStateFlow()

    fun updateNgoName(ngoName:String){
        this._ngoName.value=ngoName
    }

    //Ngo location
    private val _ngoLocation=MutableStateFlow("")
    val ngoLocation get() = _ngoLocation.asStateFlow()

    fun updateNgoLocation(ngoLocation:String){
        this._ngoLocation.value=ngoLocation
    }

    //Ngo Information
    private val _ngoInfo=MutableStateFlow("")
    val ngoInfo get() = _ngoInfo.asStateFlow()

    fun updateNgoInfo(ngoInfo:String){
        this._ngoInfo.value=ngoInfo
    }

    //Ngo Long description
    private val _ngoLongDescription=MutableStateFlow("")
    val ngoLongDescription get() = _ngoLongDescription.asStateFlow()

    fun updateNgoLongDescription(ngoLongDescription:String){
        this._ngoLongDescription.value=ngoLongDescription
    }



}