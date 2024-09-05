package com.learn.sevasahyog.ngo_home.items.event.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.auth.domain.SessionManager
import com.learn.sevasahyog.ngo_home.items.event.domain.UpcomingEventViewModel
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun EventScreen(
    navController: NavController,
    appNavController: NavController,
    viewModel: UpcomingEventViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current


    val session =
        SessionManager(context)   // session to get the user details stored in shared-preference
    val data = session.getUserDetails()
    // set token and uid to view-model for network requests
    data["token"]?.let {
        viewModel.updateAccessToken(it)
        Log.d("tokenId", it)
    }
    data["uid"]?.let {
        viewModel.updateUserId(it)
        Log.d("userId", it)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    appNavController.navigate("event/createEventScreen")
                },
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Event",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) {
        val upcomingEvents by viewModel.upcomingEvents.collectAsState()
        val pastEventYears by viewModel.pastEventYears.collectAsState()

        LaunchedEffect(Unit) {  // network call for data to be loaded on screen
            viewModel.loadUpcomingEvents()
            viewModel.loadPastEventYears()
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 18.dp),
                    text = "Events",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "UPCOMING EVENTS",
                    letterSpacing = 2.sp,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item { Spacer(modifier = Modifier.width(4.dp)) }
                    items(upcomingEvents.size) { index ->
                        val event = upcomingEvents[index]
                        UpcomingEvent(
                            eventName = event.name,
                            leadBy = event.organizer,
                            dateOfEvent = "${event.dd}/${event.mm}/${event.yyyy}"
                        ) {
                            appNavController.navigate("event/eventDetailScreen")
                        }
                    }
                    item { Spacer(modifier = Modifier.width(4.dp)) }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "PAST EVENTS",
                        letterSpacing = 2.sp,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // grid for all past year events
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(pastEventYears.reversed()){ year ->
                            PastEvent(eventYear = "$year") {
                                appNavController.navigate("event/viewEventScreen")
                            }
                        }
                        item { Spacer(modifier = Modifier.height(12.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
fun UpcomingEvent(eventName: String, leadBy: String, dateOfEvent: String, onViewEvent: () -> Unit) {
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
                onClick = { onViewEvent() }
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
        EventScreen(
            navController = rememberNavController(),
            appNavController = rememberNavController()
        )
    }
}