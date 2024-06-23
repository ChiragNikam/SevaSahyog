package com.learn.sevasahyog

import android.util.Log
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.learn.sevasahyog.auth.data.AuthService
import com.learn.sevasahyog.auth.data.dataclass.SignInRequest
import com.learn.sevasahyog.auth.domain.SessionManager
import com.learn.sevasahyog.auth.repo.AuthRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeout: (isLoggedIn: Boolean, userType: String?, isLoginSuccess: Boolean) -> Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val session = SessionManager(context = context)

    LaunchedEffect(key1 = true) {
        var isLoginSuccess = false
        scope.async {
            launch {
                delay(2000) // 2 seconds delay for the splash screen
            }
            launch {
                val auth = AuthRepo()
                val sessionData = session.getUserDetails()
                auth.ngoSignIn(
                    signInData = SignInRequest(email = sessionData["email"].toString(), password = sessionData["password"].toString()),
                    onResponse = {call, response->
                        if (response.isSuccessful){
                            val finalResponse = response.body()
                            val uid = finalResponse?.ngoAccount?.userId
                            session.updateToken(response.body()?.token, uid)
                            isLoginSuccess = true
//                            Log.d("login", "successful, token: ${response.body()?.token}")
                        }
                    },
                    onFailure = {_, throwable->
                        throwable.localizedMessage?.let { Log.d("login_fail", it) }
                    }
                )
                if (isLoginSuccess)
                    onTimeout(session.isLoggedIn(), session.getUserType(), isLoginSuccess)
                else
                    onTimeout(false, session.getUserType(), isLoginSuccess)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.iconamoon_profile_fill),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}