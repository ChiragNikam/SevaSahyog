package com.learn.sevasahyog.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun ChooseAccountType(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Choose Your \nAccount",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                        fontWeight = FontWeight.Bold
                    ),
                    letterSpacing = TextUnit(-2.2f, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                // navigate to SignUp as User
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row {
                        Text(text = "User Account")
                        Spacer(modifier = Modifier.width(18.dp))
                        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "arrow")
                    }
                }

                // navigate to SignUp as Ngo
                Button(
                    onClick = {
                              navController.navigate("auth/signUpNgo")
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row {
                        Text(text = "Ngo Account")
                        Spacer(modifier = Modifier.width(18.dp))
                        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "arrow")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChooseAccountTypePrev() {
    SevaSahyogTheme {
        ChooseAccountType(navController = rememberNavController())
    }
}