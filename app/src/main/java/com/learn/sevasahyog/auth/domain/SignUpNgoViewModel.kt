package com.learn.sevasahyog.auth.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpNgoViewModel: ViewModel() {

    // admin name
    private val _adminName = MutableStateFlow("");
    val adminName get() = _adminName.asStateFlow()

    private val _adminNameError = MutableStateFlow(false)
    val adminNameError get() = _adminNameError.asStateFlow()

    private val _adminNameErrorMessage = MutableStateFlow("")
    val adminNameErrorMessage get() = _adminNameErrorMessage.asStateFlow()

    fun updateAdminName(adminName: String) {
        this._adminName.value = adminName
    }

    fun updateAdminNameError(error: Boolean) {
        this._adminNameError.value = error
    }

    // phone no
    private val _phoneNo = MutableStateFlow("");
    val phoneNo get() = _phoneNo.asStateFlow()

    private val _phoneNoError = MutableStateFlow(false)
    val phoneNoError get() = _phoneNoError.asStateFlow()

    private val _phoneNoErrorMessage = MutableStateFlow("")
    val phoneNoErrorMessage get() = _phoneNoErrorMessage.asStateFlow()

    fun updatePhoneNo(phoneNo: String) {
        this._phoneNo.value = phoneNo
    }

    fun updatePhoneNoError(error: Boolean) {
        this._phoneNoError.value = error
    }

    // email
    private val _email = MutableStateFlow("");
    val email get() = _email.asStateFlow()

    private val _emailError = MutableStateFlow(false)
    val emailError get() = _email.asStateFlow()

    private val _emailErrorMessage = MutableStateFlow("")
    val emailErrorMessage get() = _emailErrorMessage.asStateFlow()

    fun updateEmail(phoneNo: String) {
        this._phoneNo.value = phoneNo
    }

    fun updateEmailError(error: Boolean) {
        this._phoneNoError.value = error
    }

    // password
    private val _password = MutableStateFlow("");
    val password get() = _password.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError get() = _passwordError.asStateFlow()

    private val _passwordErrorMessage = MutableStateFlow("")
    val passwordErrorMessage get() = _passwordErrorMessage.asStateFlow()

    fun updatePassword(phoneNo: String) {
        this._phoneNo.value = phoneNo
    }

    fun updatePasswordError(error: Boolean) {
        this._phoneNoError.value = error
    }

}