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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun SignUpNgo(navController: NavController) {
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
                    label = { Text("Admin Name*") },
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
                // password
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                )
                // confirm password
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Confirm Password") },
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Ngo Name*") },
                )
                // location
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Location*") },
                )
                // short description
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Short Description") },
                )
                // long description
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Moto, What you focus upon") },
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
private fun SignUpNgoPrev() {
    SevaSahyogTheme {
        SignUpNgo(navController = rememberNavController())
    }
}