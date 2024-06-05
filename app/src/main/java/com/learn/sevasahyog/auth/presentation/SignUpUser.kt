package com.learn.sevasahyog.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.R
import com.learn.sevasahyog.auth.common.ValidatedTextField
import com.learn.sevasahyog.auth.common.ValidatedTextFieldPassword
import com.learn.sevasahyog.auth.domain.SignUpUserViewModel
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun SingUpUser(
    navController: NavController,
    viewModel: SignUpUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Create User \nAccount",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = TextUnit(-1.8f, TextUnitType.Sp)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "PERSONAL DETAILS",
                        style = MaterialTheme.typography.labelMedium.copy(
                            letterSpacing = TextUnit(1.8f, TextUnitType.Sp)
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(
                        modifier = Modifier
                            .weight(0.8f)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.inverseSurface)
                    ) {}
                }

                // user profile
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .border(
                            BorderStroke(8.dp, MaterialTheme.colorScheme.secondary.copy(0.8f)),
                            shape = CircleShape
                        )
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "profile picture",
                        tint = MaterialTheme.colorScheme.secondary.copy(0.8f),
                        modifier = Modifier.size(126.dp)
                    )
                }
                // user name
                val userName by viewModel.userName.collectAsState()
                val userNameError by viewModel.userNameError.collectAsState()
                val userNameErrorMessage by viewModel.userNameErrorMessage.collectAsState()
                ValidatedTextField(
                    textFieldModifier = Modifier.fillMaxWidth(),
                    label = "User Name*",
                    value = userName,
                    error = userNameError,
                    errorMessage = userNameErrorMessage
                ) {
                    viewModel.updateUserNameError(false)
                    viewModel.updateUserName(it)
                }

                // phone no
                val phoneNo by viewModel.phoneNo.collectAsState()
                val phoneNoError by viewModel.phoneNoError.collectAsState()
                val phoneNoErrorMessage by viewModel.phoneNoErrorMessage.collectAsState()
                ValidatedTextField(
                    textFieldModifier = Modifier.fillMaxWidth(),
                    label = "Phone No.*",
                    value = phoneNo,
                    error = phoneNoError,
                    errorMessage = phoneNoErrorMessage,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                ) {
                    viewModel.updatePhoneNoError(false)
                    viewModel.updatePhoneNo(it)
                }

                // email
                val email by viewModel.email.collectAsState()
                val emailError by viewModel.emailError.collectAsState()
                val emailErrorMessage by viewModel.emailErrorMessage.collectAsState()
                ValidatedTextField(
                    textFieldModifier = Modifier.fillMaxWidth(),
                    label = "Email*",
                    value = email,
                    error = emailError,
                    errorMessage = emailErrorMessage,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                ) {
                    viewModel.updateEmailError(false)
                    viewModel.updateEmail(it)
                }

                // password
                var passwordVisible by remember { mutableStateOf(false) }
                val password by viewModel.password.collectAsState()
                val passwordError by viewModel.passwordError.collectAsState()
                val passwordErrorMessage by viewModel.passwordErrorMessage.collectAsState()
                ValidatedTextFieldPassword(
                    label = "Password",
                    value = password,
                    error = passwordError,
                    errorMessage = passwordErrorMessage,
                    passwordVisible = passwordVisible,
                    onValueChange = {
                        viewModel.updatePasswordError(false)
                        viewModel.updatePassword(it)
                    },
                    onIconClick = {
                        passwordVisible = !passwordVisible
                    }
                )

                // confirm password
                var confirmPasswordVisible by remember { mutableStateOf(false) }
                val confirmPassword by viewModel.confirmPassword.collectAsState()
                val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()
                val confirmPasswordErrorMessage by viewModel.confirmPasswordErrorMessage.collectAsState()
                ValidatedTextFieldPassword(
                    label = "Password",
                    value = confirmPassword,
                    error = confirmPasswordError,
                    errorMessage = confirmPasswordErrorMessage,
                    passwordVisible = confirmPasswordVisible,
                    onValueChange = {
                        viewModel.updateConfirmPasswordError(false)
                        viewModel.updateConfirmPassword(it)
                    },
                    onIconClick = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }
                )
            }

            Spacer(modifier = Modifier.height(42.dp))

            // create account
            Button(
                onClick = {
                          viewModel.validateSignUpUserData()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                Text(text = "Create Account")
            }
        }
    }
}

@Preview
@Composable
private fun SignUpUserPrev() {
    SevaSahyogTheme {
        SingUpUser(rememberNavController())
    }
}