package com.learn.sevasahyog.auth.domain

import android.content.Context
import android.content.SharedPreferences


class SessionManager(context: Context?) {

    private val PREF_NAME = "LoginSession"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"
    private val KEY_TOKEN = "token"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val KEY_UID = "uid"
    private val KEY_USER_TYPE = "ngo"

    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var PRIVATE_MODE: Int = 0

    init {
        if (context != null) {
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        }
        editor = pref?.edit()
    }

    fun createLoginSession(
        token: String?,
        email: String?,
        password: String?,
        uid: String?,
        userType: String
    ) {
        editor!!.putBoolean(KEY_IS_LOGGED_IN, true)
        editor!!.putString(KEY_TOKEN, token)
        editor!!.putString(KEY_EMAIL, email)
        editor!!.putString(KEY_PASSWORD, password)
        editor!!.putString(KEY_UID, uid)
        editor!!.putString(KEY_USER_TYPE, userType)
        editor!!.commit()
    }

    fun updateToken(token: String?, uid: String?){
        editor!!.putString(KEY_TOKEN, token)
        editor!!.putString(KEY_UID, uid)
        editor!!.commit()
    }

    fun checkLogin() {
        if (!this.isLoggedIn()) {
            // If user is not logged in, redirect to login activity
            // Intent intent = new Intent(context, LoginActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // context.startActivity(intent);
        }
    }

    fun getUserDetails(): HashMap<String, String?> {
        val user = HashMap<String, String?>()
        user[KEY_TOKEN] = pref!!.getString(KEY_TOKEN, null)
        user[KEY_EMAIL] = pref!!.getString(KEY_EMAIL, null)
        user[KEY_PASSWORD] = pref!!.getString(KEY_PASSWORD, null)
        user[KEY_UID] = pref!!.getString(KEY_UID, null)
        return user
    }

    fun logoutUser(): Boolean {
        editor!!.clear()
        editor!!.commit()
        return true
    }

    fun getUserType(): String? {
        return pref!!.getString(KEY_USER_TYPE, "user")
    }

    fun isLoggedIn(): Boolean {
        return pref!!.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}