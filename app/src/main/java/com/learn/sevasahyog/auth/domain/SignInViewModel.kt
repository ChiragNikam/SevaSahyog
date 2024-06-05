package com.learn.sevasahyog.auth.domain

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Error

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

    fun updateSelectedUserAccount(selected: Boolean){
        _userAccount.value = selected
    }

    private val _ngoAccount = MutableStateFlow(false)
    val ngoAccount get() = _ngoAccount.asStateFlow()

    fun updateSelectedNgoAccount(selected: Boolean){
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

    private val _loginError = MutableStateFlow(false)
    val loginError get() = _loginError.asStateFlow()

    private val _loginErrorMessage = MutableStateFlow("")

    fun login() {

    }
}