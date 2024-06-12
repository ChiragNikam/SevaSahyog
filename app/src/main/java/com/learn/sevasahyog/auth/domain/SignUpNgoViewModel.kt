package com.learn.sevasahyog.auth.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.learn.sevasahyog.auth.data.dataclass.ErrorResponse
import com.learn.sevasahyog.auth.data.dataclass.SignInRequest
import com.learn.sevasahyog.auth.data.dataclass.SignUpRequestNgo
import com.learn.sevasahyog.auth.repo.AuthRepo
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
        this._passwordError.value = error
    }

    // confirm password
    private val _confirmPass = MutableStateFlow("");
    val confirmPass get() = _confirmPass.asStateFlow()

    private val _confirmPassError = MutableStateFlow(false)
    val confirmPassError get() = _confirmPassError.asStateFlow()

    private val _confirmPassErrorMessage = MutableStateFlow("")
    val confirmPassErrorMessage get() = _confirmPassErrorMessage.asStateFlow()

    fun updateConfirmPass(confirmPass: String) {
        this._confirmPass.value = confirmPass
    }

    fun updateConfirmPassError(error: Boolean) {
        this._confirmPassError.value = error
    }

    // ngo name
    private val _ngoName = MutableStateFlow("");
    val ngoName get() = _ngoName.asStateFlow()

    private val _ngoNameError = MutableStateFlow(false)
    val ngoNameError get() = _ngoNameError.asStateFlow()

    private val _ngoNameErrorMessage = MutableStateFlow("")
    val ngoNameErrorMessage get() = _ngoNameErrorMessage.asStateFlow()

    fun updateNgoName(ngoName: String) {
        this._ngoName.value = ngoName
    }

    fun updateNgoError(error: Boolean) {
        this._ngoNameError.value = error
    }

    // location
    private val _location = MutableStateFlow("");
    val location get() = _location.asStateFlow()

    private val _locationError = MutableStateFlow(false)
    val locationError get() = _locationError.asStateFlow()

    private val _locationErrorMessage = MutableStateFlow("")
    val locationErrorMessage get() = _locationErrorMessage.asStateFlow()

    fun updateLocation(ngoName: String) {
        this._location.value = ngoName
    }

    fun updateLocationError(error: Boolean) {
        this._locationError.value = error
    }

    // short description
    private val _shortDesc = MutableStateFlow("");
    val shortDesc get() = _shortDesc.asStateFlow()

    fun updateShortDesc(shortDesc: String) {
        this._shortDesc.value = shortDesc
    }

    // long description
    private val _longDesc = MutableStateFlow("");
    val longDesc get() = _longDesc.asStateFlow()

    fun updateLongDesc(longDesc: String) {
        this._longDesc.value = longDesc
    }

    // error
    private val _signUpError = MutableStateFlow(false)
    val signUpError get() = _signUpError

    private val _signUpErrorMessage = MutableStateFlow("")
    val signUpErrorMessage get() = _signUpErrorMessage

    // signIn token
    private val _signInToken = MutableStateFlow("")
    val signInToken get() = _signInToken

    // user id
    private val _userId = MutableStateFlow("")
    val userId get() = _userId

    // signIn error
    private val _signInError = MutableStateFlow(false)
    val signInError get() = _signInError.asStateFlow()

    private val _signInErrorMessage = MutableStateFlow("")
    val signInErrorMessage get() = _signInErrorMessage

    fun updateSignInError(error: Boolean) {
        _signInError.value = error
    }

    // success
    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess get() = _signUpSuccess

    private val _signInSuccess = MutableStateFlow(false)
    val signInSuccess get() = _signInSuccess

    val authRepo = AuthRepo()
    fun signUpAsNgo(){
        val signUpData = SignUpRequestNgo(      // create obj for signUp data
            userName = adminName.value,
            mobileNo = phoneNo.value,
            email = email.value,
            password = password.value,
            ngoName = ngoName.value,
            location = location.value,
            aboutNgo = longDesc.value
        )
        
        authRepo.ngoSignUp(
            signUpData = signUpData,
            onResponse = {call, response ->  
                if (response.code() == 200){
                    val signUpResponse = response.body()
                    if (signUpResponse != null){
                        Log.d("sign_up", "success")
                        _signUpSuccess.value = true
                        // after creating account do signIn
                        authRepo.ngoSignIn(
                            signInData = SignInRequest(email = email.value, password = password.value),
                            onResponse = { call, signInResponse ->
                                if (signInResponse.code() == 200) {
                                    _signInSuccess.value = true
                                    val signInResponseData = signInResponse.body()
                                    if (signInResponseData != null){
                                        _signInToken.value = signInResponseData.token
                                        _userId.value = signInResponseData.ngoAccount.userId
                                    }
                                } else if (signInResponse.code() == 409) {
                                    val errorBody = response.errorBody()?.string()
                                    val errorResponse = errorBody.let {
                                        Gson().fromJson(it, ErrorResponse::class.java)
                                    }
                                    _signInError.value = true
                                    _signInErrorMessage.value = errorResponse.errorMessage
                                    Log.e("login_error", errorResponse.errorMessage)
                                } else {
                                    Log.i("login_success", "success")
                                }
                            },
                            onFailure = { call, t ->
                                t.message?.let { Log.e("login_error", it) }
                                _signInError.value = true
                                _signInErrorMessage.value = t.message.toString()
                            }
                        )
                    }
                }
            },
            onFailure = {call, t ->
                t.message?.let { Log.e("sign_up_error", it) }
                _signUpError.value = true
                _signUpErrorMessage.value = t.message.toString()
            }
        )
    }

}