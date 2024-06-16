package com.learn.sevasahyog.ngo_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun EventScreen(navController: NavController){
    Surface(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Event",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight(700)
            )
        }
    }

}

@Preview
@Composable
private fun PreviewEventScreen() {
    EventScreen(navController = rememberNavController())
    
}