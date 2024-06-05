package com.learn.sevasahyog.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.R
import com.learn.sevasahyog.auth.domain.SignInViewModel
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun SignIn(
    navController: NavController,
    viewModel: SignInViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                    fontWeight = FontWeight.Bold
                ),
                letterSpacing = TextUnit(-2.2f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "Please sign in to continue",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.88f),
                    fontWeight = FontWeight.Bold
                ),
                letterSpacing = TextUnit(-1.2f, TextUnitType.Sp),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // email
            val email by viewModel.email.collectAsState()
            val emailError by viewModel.emailError.collectAsState()
            val emailErrorMessage by viewModel.emailErrorMessage.collectAsState()
            OutlinedTextField(
                value = email,
                onValueChange = {
                    viewModel.updateEmailError(false)
                    viewModel.updateEmail(it)
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = emailError
            )
            if (emailError) {    // error message of email
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = emailErrorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // password
            val password by viewModel.password.collectAsState()
            val passwordError by viewModel.passwordError.collectAsState()
            val passwordErrorMessage by viewModel.passwordErrorMessage.collectAsState()
            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = {
                    viewModel.updatePasswordError(false)
                    viewModel.updatePassword(it)
                },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    val image = if (passwordVisible)
                        painterResource(id = R.drawable.ic_visible)
                    else painterResource(id = R.drawable.ic_not_visible)

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = image, contentDescription = null)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                isError = passwordError
            )
            if (passwordError) { // error message of password
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = passwordErrorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            // login
            var loginProgress by remember {
                mutableStateOf(false)
            }
            Button(
                onClick = {
                    val isDataOk = viewModel.validateSignInData()
                    if(isDataOk){
                        loginProgress = true
                        viewModel.login()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                    Text("Login")
                    if (loginProgress){
                        Spacer(modifier = Modifier.width(18.dp))
                        CircularProgressIndicator(trackColor = MaterialTheme.colorScheme.onPrimary)
                    }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("or")
            Spacer(modifier = Modifier.height(8.dp))

            // create account
            OutlinedButton(
                onClick = {
                    navController.navigate("auth/chooseAccountType")
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Create an account", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Visit Us",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Google"
                    )
                }
                IconButton(onClick = {}) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Facebook"
                    )
                }
                IconButton(onClick = {}) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Instagram"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignInPreview() {
    SevaSahyogTheme {
        SignIn(navController = rememberNavController())
    }
}