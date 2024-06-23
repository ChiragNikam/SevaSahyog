package com.learn.sevasahyog.auth.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.learn.sevasahyog.auth.data.dataclass.ErrorResponse
import com.learn.sevasahyog.auth.data.dataclass.SignInRequest
import com.learn.sevasahyog.auth.repo.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    // email
    private val _email = MutableStateFlow("")
    val email get() = _email.asStateFlow()

    private val _emailError = MutableStateFlow(false)
    val emailError get() = _emailError.asStateFlow()

    private val _emailErrorMessage = MutableStateFlow("")
    val emailErrorMessage get() = _emailErrorMessage.asStateFlow()

    fun updateEmail(updatedEmail: String) {
        this._email.value = updatedEmail
    }

    fun updateEmailError(updatedEmailError: Boolean) {
        this._emailError.value = updatedEmailError
    }

    fun updateEmailErrorMessage(updatedEmailErrorMessage: String) {
        this._emailErrorMessage.value = updatedEmailErrorMessage
    }

    // password
    private val _password = MutableStateFlow("")
    val password get() = _password.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError get() = _passwordError.asStateFlow()

    private val _passwordErrorMessage = MutableStateFlow("")
    val passwordErrorMessage get() = _passwordErrorMessage.asStateFlow()

    fun updatePassword(updatedPassword: String) {
        this._password.value = updatedPassword
    }

    fun updatePasswordError(updatePasswordError: Boolean) {
        this._passwordError.value = updatePasswordError
    }

    fun updatePasswordErrorMessage(updatedPasswordErrorMessage: String) {
        this._passwordErrorMessage.value = updatedPasswordErrorMessage
    }

    // account type
    private val _userAccount = MutableStateFlow(true)
    val userAccount get() = _userAccount.asStateFlow()

    fun isUserAccountSelected(selected: Boolean) {
        _userAccount.value = selected
    }

    private val _ngoAccount = MutableStateFlow(false)
    val ngoAccount get() = _ngoAccount.asStateFlow()

    fun isNgoAccountSelected(selected: Boolean) {
        _ngoAccount.value = selected
    }

    fun validateSignInData(): Boolean {
        if (email.value.isEmpty()) {
            _emailError.value = true
            _emailErrorMessage.value = "Please enter email"
        }
        if (password.value.isEmpty()) {
            _passwordError.value = true
            _passwordErrorMessage.value = "Please enter password"
        }

        return !(emailError.value && passwordError.value)
    }

    // signIn error
    private val _signInError = MutableStateFlow(false)
    val signInError get() = _signInError.asStateFlow()

    private val _signInErrorMessage = MutableStateFlow("")
    val signInErrorMessage get() = _signInErrorMessage

    fun updateSignInError(error: Boolean) {
        _signInError.value = error
    }

    // signIn progress
    private val _signInProgress = MutableStateFlow(false)
    val signInProgress get() = _signInProgress.asStateFlow()

    fun updateSignInProgress(progress: Boolean) {
        _signInProgress.value = progress
    }

    // signIn token
    private val _signInToken = MutableStateFlow("")
    val signInToken get() = _signInToken

    // user id
    private val _userId = MutableStateFlow("")
    val userId get() = _userId

    // login success
    private val _ngoSignInSuccess = MutableStateFlow(false)
    val ngoSignInSuccess get() = _ngoSignInSuccess

    fun login() {
        viewModelScope.launch {
            if (ngoAccount.value) { // if ngo account selected
                loginAsNgo()
            } else {    // if user account selected
                loginAsUser()
            }
        }
    }

    private val authRepo = AuthRepo()
    private fun loginAsUser() {

    }

    private fun loginAsNgo() {
        viewModelScope.launch {
            authRepo.ngoSignIn(
                signInData = SignInRequest(email = email.value, password = password.value),
                onResponse = { call, response ->
                    if (response.code() == 200) {
                        _ngoSignInSuccess.value = true
                        val signInResponse = response.body()
                        if (signInResponse != null){
                            _signInToken.value = signInResponse.token
                            _userId.value = signInResponse.ngoAccount.userId
                        }
                        Log.i("login_success", "success")
                    } else if (response.code() == 409) {
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = errorBody.let {
                            Gson().fromJson(it, ErrorResponse::class.java)
                        }
                        _signInError.value = true
                        _signInErrorMessage.value = errorResponse.errorMessage
                        Log.e("login_error", errorResponse.errorMessage)
                    }
                    _signInProgress.value = false
                    Log.d("code", response.code().toString())
                },
                onFailure = { call, t ->
                    t.message?.let { Log.e("login_error", it) }
                    _signInError.value = true
                    _signInErrorMessage.value = t.message.toString()
                    _signInProgress.value = false
                }
            )
        }
    }
}