package com.learn.sevasahyog.ngo_home.items.event.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.ngo_home.items.event.data.EventByYear

@Composable
fun ViewEventsScreen(appNavController: NavController) {

    val events = listOf(
        EventByYear(name = "Event 1", date = "2024-01-15"),
        EventByYear(name = "Event 2", date = "2024-03-22"),
        EventByYear(name = "Event 3", date = "2024-06-10"),
        EventByYear(name = "Event 4", date = "2024-08-05"),
        EventByYear(name = "Event 5", date = "2024-12-20")
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "2024",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight(700)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Events ",
                style = MaterialTheme.typography.labelLarge,
                letterSpacing = 4.sp
            )
            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn {
                items(events) { event ->
                    EventItem(event = event, onClick = {
                        appNavController.navigate("event/eventItemByYearScreen")
                    })
                }
            }

        }
    }
}

@Composable
fun EventItem(event: EventByYear, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = event.date,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ViewEventsScreenPreview() {
    ViewEventsScreen(appNavController = rememberNavController())
}