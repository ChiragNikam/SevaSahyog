package com.learn.sevasahyog.auth.presentation


import android.provider.MediaStore.Images
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.auth.common.ValidatedTextField
import com.learn.sevasahyog.auth.common.ValidatedTextFieldPassword
import com.learn.sevasahyog.auth.domain.SignUpNgoViewModel
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun SignUpNgo(
    navController: NavController,
    viewModel: SignUpNgoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
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
                text = "Create Admin \nAccount",
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

                // user data
                val adminName by viewModel.adminName.collectAsState()
                val adminNameError by viewModel.adminNameError.collectAsState()
                val adminNameErrorMessage by viewModel.adminNameErrorMessage.collectAsState()

                val phoneNo by viewModel.phoneNo.collectAsState()
                val phoneNoError by viewModel.phoneNoError.collectAsState()
                val phoneNoErrorMessage by viewModel.phoneNoErrorMessage.collectAsState()

                val email by viewModel.email.collectAsState()
                val emailError by viewModel.emailError.collectAsState()
                val emailErrorMessage by viewModel.emailErrorMessage.collectAsState()

                val password by viewModel.password.collectAsState()
                val passwordError by viewModel.passwordError.collectAsState()
                val passwordErrorMessage by viewModel.passwordErrorMessage.collectAsState()

                val confirmPass by viewModel.confirmPass.collectAsState()
                val confirmPassError by viewModel.confirmPassError.collectAsState()
                val confirmPassErrorMessage by viewModel.confirmPassErrorMessage.collectAsState()

                // user profile photo
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
                // admin name
                ValidatedTextField(
                    label = "Admin Name*",
                    value = adminName,
                    error = adminNameError,
                    errorMessage = adminNameErrorMessage
                ) {
                    viewModel.updateAdminName(it)
                }

                // phone no
                ValidatedTextField(
                    label = "Phone No.*",
                    value = phoneNo,
                    error = phoneNoError,
                    errorMessage = phoneNoErrorMessage
                ) {
                    viewModel.updatePhoneNo(it)
                }

                // email
                ValidatedTextField(
                    label = "Email*",
                    value = email,
                    error = emailError,
                    errorMessage = emailErrorMessage
                ) {
                    viewModel.updateEmail(it)
                }

                // password
                var passwordVisible by remember { mutableStateOf(false) }
                ValidatedTextFieldPassword(
                    label = "Password",
                    value = password,
                    error = passwordError,
                    errorMessage = passwordErrorMessage,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    passwordVisible = passwordVisible,
                    onIconClick = { passwordVisible = !passwordVisible },
                    onValueChange = {
                        viewModel.updatePassword(it)
                    }
                )

                // confirm password
                var confirmPasswordVisible by remember { mutableStateOf(false) }
                ValidatedTextFieldPassword(
                    label = "Confirm Password",
                    value = confirmPass,
                    error = confirmPassError,
                    errorMessage = confirmPassErrorMessage,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    passwordVisible = confirmPasswordVisible,
                    onIconClick = { confirmPasswordVisible = !confirmPasswordVisible },
                    onValueChange = {
                        viewModel.updateConfirmPass(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // ngo data
                val ngoName by viewModel.ngoName.collectAsState()
                val ngoNameError by viewModel.ngoNameError.collectAsState()
                val ngoNameErrorMessage by viewModel.ngoNameErrorMessage.collectAsState()

                val location by viewModel.location.collectAsState()
                val locationError by viewModel.locationError.collectAsState()
                val locationErrorMessage by viewModel.locationErrorMessage.collectAsState()

                val shortDesc by viewModel.shortDesc.collectAsState()
                val longDesc by viewModel.longDesc.collectAsState()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "NGO DETAILS",
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

                // ngo logo or image
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Nog Logo or Image")
                }
                // ngo name
                ValidatedTextField(
                    label = "Ngo Name*",
                    value = ngoName,
                    error = ngoNameError,
                    errorMessage = ngoNameErrorMessage,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                ) {
                    viewModel.updateNgoName(it)
                }

                // location
                ValidatedTextField(
                    label = "Location*",
                    value = location,
                    error = locationError,
                    errorMessage = locationErrorMessage
                ) {
                    viewModel.updateLocation(it)
                }

                // short description
                OutlinedTextField(
                    value = shortDesc,
                    onValueChange = {
                        if (it.length <= 150)
                            viewModel.updateShortDesc(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Short Description(within 150 characters)") },
                )
                // long description
                OutlinedTextField(
                    value = longDesc,
                    onValueChange = {
                        if (it.length <= 1000)
                            viewModel.updateLongDesc(it)
                    },
                    modifier = Modifier.height(156.dp).fillMaxWidth(),
                    label = { Text("Moto, What you focus upon (1000 words)") },
                )
            }

            Spacer(modifier = Modifier.height(42.dp))

            // create account
            Button(
                onClick = {},
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
private fun SignUpNgoPrev() {
    SevaSahyogTheme {
        SignUpNgo(navController = rememberNavController())
    }
}