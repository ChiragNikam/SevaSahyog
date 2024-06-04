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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun SingUpUser(navController: NavController) {
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
                // admin name
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("User Name*") },
                )
                // phone no
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Phone No.*") },
                )
                // email
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email*") },
                )

                var passwordVisible by remember { mutableStateOf(false) }
                // password
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    trailingIcon = {
                        val image = if (passwordVisible)
                            painterResource(id = R.drawable.ic_visible)
                        else painterResource(id = R.drawable.ic_not_visible)

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, contentDescription = null)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                var confirmPasswordVisible by remember { mutableStateOf(false) }
                // confirm password
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Confirm Password") },
                    trailingIcon = {
                        val image = if (confirmPasswordVisible)
                            painterResource(id = R.drawable.ic_visible)
                        else painterResource(id = R.drawable.ic_not_visible)

                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(painter = image, contentDescription = null)
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )
            }

            Spacer(modifier = Modifier.height(42.dp))

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
private fun SignUpUserPrev() {
    SevaSahyogTheme {
        SingUpUser(rememberNavController())
    }
}