package com.learn.sevasahyog.ngo_home.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel: ViewModel() {
    private val _userName= MutableStateFlow("")
    val userName get() = _userName.asStateFlow()

    fun updateUserName(userName:String){
        this._userName.value=userName
    }
}