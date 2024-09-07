package com.learn.sevasahyog.ngo_home.items.event.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.auth.domain.SessionManager
import com.learn.sevasahyog.ngo_home.items.event.data.Event
import com.learn.sevasahyog.ngo_home.items.event.data.EventDummy
import com.learn.sevasahyog.ngo_home.items.event.domain.EventsViewModel

@Composable
fun ViewEventsScreen(
    appNavController: NavController,
    viewModel: EventsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    yearOfEvents: Int
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val eventByYearList by viewModel.eventsByYearList.collectAsState()
    val selectedEventYear by viewModel.selectedEventYear.collectAsState()

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

    LaunchedEffect(Unit) {
        viewModel.loadEventByUser(yearOfEvents)
        Log.d("events_at_$selectedEventYear", eventByYearList.toString())
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    appNavController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
                Text(
                    text = "$yearOfEvents",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight(700)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Events ",
                style = MaterialTheme.typography.labelLarge,
                letterSpacing = 4.sp
            )
            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn {
                items(eventByYearList) { event ->
                    EventItem(event = event, onClick = {
                        appNavController.navigate("event/eventDetailScreen")
                    })
                }
            }

        }
    }
}

@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
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
                text = event.longDesc,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Row {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location"
                    )

                    Text(
                        text = event.location.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Row {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Date"
                    )

                    Text(
                        text = "${event.dd}/${event.mm}/${event.yyyy}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ViewEventsScreenPreview() {
    ViewEventsScreen(appNavController = rememberNavController(), yearOfEvents = 2021)
}