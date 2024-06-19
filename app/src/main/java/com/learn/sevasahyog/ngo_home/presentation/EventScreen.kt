package com.learn.sevasahyog.ngo_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun EventScreen(navController: NavController, appNavController: NavController) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    appNavController.navigate("event/createEventScreen")
                },
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Event", tint = MaterialTheme.colorScheme.primary)
            }
        }
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(start = 10.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = "Events",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "UPCOMING EVENTS", style = MaterialTheme.typography.labelLarge, letterSpacing = MaterialTheme.typography.labelMedium.letterSpacing)

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(8.dp)
                ) {

                    UpcomingEvent(
                        eventName = "Office Meeting",
                        leadBy = "Rajat kr",
                        dateOfEvent = "12/08/2024"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    UpcomingEvent(
                        eventName = "Fruits distribution",
                        leadBy = "Mohan",
                        dateOfEvent = "19/08/2024"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    UpcomingEvent(
                        eventName = "awareness scheme",
                        leadBy = "Chirag",
                        dateOfEvent = "10/02/2024"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "PAST EVENTS", style = MaterialTheme.typography.labelLarge, letterSpacing = MaterialTheme.typography.labelMedium.letterSpacing)

                Spacer(modifier = Modifier.height(10.dp))

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(8){
                        PastEvent(eventYear = "2024", onClick = {
                            appNavController.navigate("event/eventDetailScreen")
                        })
                    }
                }
            }
        }
    }

}

@Composable
fun UpcomingEvent(eventName: String, leadBy: String, dateOfEvent: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = eventName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = leadBy,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = dateOfEvent,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))

            ClickableText(
                text = AnnotatedString("View Details"),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {}
            )
        }
    }
}

@Composable
fun PastEvent(eventYear: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(110.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )

    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = eventYear,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "View Events",
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewEventScreen() {
    SevaSahyogTheme {
        EventScreen(navController = rememberNavController(), appNavController = rememberNavController())
    }
}