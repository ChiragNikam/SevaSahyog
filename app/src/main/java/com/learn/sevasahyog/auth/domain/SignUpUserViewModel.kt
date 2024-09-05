package com.learn.sevasahyog.auth.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpUserViewModel : ViewModel() {

    // common Error
    private val _commonError = MutableStateFlow(false);
    val commonError get() = _commonError.asStateFlow()

    private val _commonErrorMessage = MutableStateFlow("Something Went Wrong");

    val commonErrorMessage get() = _commonErrorMessage.asStateFlow()

    fun updateCommonError(error: Boolean) {
        _commonError.value = error
    }

    // user name
    private val _userName = MutableStateFlow("");
    val userName get() = _userName.asStateFlow()

    private val _userNameError = MutableStateFlow(false)
    val userNameError get() = _userNameError.asStateFlow()

    private val _userNameErrorMessage = MutableStateFlow("")
    val userNameErrorMessage get() = _userNameErrorMessage.asStateFlow()

    fun updateUserName(userName: String) {
        this._userName.value = userName
    }

    fun updateUserNameError(error: Boolean) {
        this._userNameError.value = error
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
    val emailError get() = _emailError.asStateFlow()

    private val _emailErrorMessage = MutableStateFlow("")
    val emailErrorMessage get() = _emailErrorMessage.asStateFlow()

    fun updateEmail(email: String) {
        this._email.value = email
    }

    fun updateEmailError(error: Boolean) {
        this._emailError.value = error
    }

    // password
    private val _password = MutableStateFlow("");
    val password get() = _password.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError get() = _passwordError.asStateFlow()

    private val _passwordErrorMessage = MutableStateFlow("")
    val passwordErrorMessage get() = _passwordErrorMessage.asStateFlow()

    fun updatePassword(password: String) {
        this._password.value = password
    }

    fun updatePasswordError(error: Boolean) {
        _passwordError.value = error
    }

    // confirm password
    private val _confirmPassword = MutableStateFlow("");
    val confirmPassword get() = _confirmPassword.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow(false)
    val confirmPasswordError get() = _confirmPasswordError.asStateFlow()

    private val _confirmPasswordErrorMessage = MutableStateFlow("")
    val confirmPasswordErrorMessage get() = _confirmPasswordErrorMessage.asStateFlow()

    fun updateConfirmPassword(confirmPassword: String) {
        this._confirmPassword.value = confirmPassword
    }

    fun updateConfirmPasswordError(error: Boolean) {
        this._confirmPasswordError.value = error
    }

    fun validateSignUpUserData(): Boolean {
        if (userName.value.isEmpty()) {
            _userNameError.value = true
            _userNameErrorMessage.value = "Please enter user-name"
        }
        if (phoneNo.value.isEmpty()) {
            _phoneNoError.value = true
            _phoneNoErrorMessage.value = "Please enter phone"
        }
        if (email.value.isEmpty()) {
            _emailError.value = true
            _emailErrorMessage.value = "Please enter email"
        }
        if (password.value.isEmpty()) {
            _passwordError.value = true
            _passwordErrorMessage.value = "Please enter password"
        }
        if (password.value != confirmPassword.value) {
            _confirmPasswordError.value = true
            _confirmPasswordErrorMessage.value = "Didn't mach with password"
        }

        return !(userNameError.value && phoneNoError.value && emailError.value && passwordError.value && confirmPasswordError.value)
    }
}