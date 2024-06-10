package com.learn.sevasahyog.auth.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.learn.sevasahyog.auth.data.dataclass.ErrorResponse
import com.learn.sevasahyog.auth.data.dataclass.SignInRequest
import com.learn.sevasahyog.auth.data.dataclass.SignInResponseNgo
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

    fun updateSelectedUserAccount(selected: Boolean) {
        _userAccount.value = selected
    }

    private val _ngoAccount = MutableStateFlow(false)
    val ngoAccount get() = _ngoAccount.asStateFlow()

    fun updateSelectedNgoAccount(selected: Boolean) {
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
    private val _loginError = MutableStateFlow(false)
    val loginError get() = _loginError.asStateFlow()

    private val _loginErrorMessage = MutableStateFlow("")
    val loginErrorMessage get() = _loginErrorMessage

    fun updateLoginError(error: Boolean) {
        _loginError.value = error
    }

    // signIn progress
    private val _loginProgress = MutableStateFlow(false)
    val loginProgress get() = _loginProgress.asStateFlow()

    fun updateLoginProgress(progress: Boolean) {
        _loginProgress.value = progress
    }

    fun login() {
        viewModelScope.launch {
            if (ngoAccount.value) { // if ngo account selected
                loginAsNgo()
            } else {    // if user account selected
                loginAsUser()
            }
            _loginProgress.value = false
        }
    }

    private val authRepo = AuthRepo()
    private fun loginAsUser() {

    }

    private fun loginAsNgo() {
        authRepo.ngoSignIn(
            signInData = SignInRequest(email = email.value, password = password.value),
            onResponse = { call, response ->
                if (response.code() == 409) {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = errorBody.let {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    }
                    _loginError.value = true
                    _loginErrorMessage.value = errorResponse.errorMessage
                    Log.e("login_error", errorResponse.errorMessage)
                } else {
                    Log.i("login_success", "success")
                }
            },
            onFailure = { call, t ->
                t.message?.let { Log.e("login_error", it) }
                _loginError.value = true
                _loginErrorMessage.value = t.message.toString()
            }
        )
    }
}