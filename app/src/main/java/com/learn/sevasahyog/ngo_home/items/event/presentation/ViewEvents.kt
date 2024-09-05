package com.learn.sevasahyog.ngo_home.items.event.presentation

import android.util.Log
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
import com.learn.sevasahyog.ngo_home.items.event.domain.EventViewModel
import com.learn.sevasahyog.ngo_home.items.event.domain.EventsViewModel

@Composable
fun ViewEventsScreen(appNavController: NavController,
                     viewModel: EventsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current


    val session =
        SessionManager(context)   // session to get the user details stored in shared-preference
    val data = session.getUserDetails()
    // set token and uid to view-model for network requests
    data["token"]?.let { viewModel.updateAccessToken(it)
        Log.d("tokenId",it)}
    data["uid"]?.let { viewModel.updateUserId(it)
    Log.d("userId",it)}




    viewModel.loadEventByUser()

    val dummyEvents = listOf(
        EventDummy(eventName = "Tea and Biscuit Distribution", description = "Distribution of tea and biscuits to the needy", eventLocation = "Bihar"),
        EventDummy(eventName = "Clothes Distribution", description = "Distributing clothes to underprivileged people", eventLocation = "Himachal" ),
        EventDummy(eventName = "Health Camp", description = "Organizing a health camp for the community", eventLocation = "Delhi"),
        EventDummy(eventName = "Educational Workshop", description = "Conducting an educational workshop for children", eventLocation = "Rajasthan"),
        EventDummy(eventName = "Tree Plantation Drive", description = "Planting trees in the local community", eventLocation = "Pune")
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
                items(dummyEvents) { event ->
                    EventItem(event =event, onClick = {
                        appNavController.navigate("event/eventDetailScreen")
                    })
                }
            }

        }
    }
}

@Composable
fun EventItem(event: EventDummy, onClick: () -> Unit) {
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
                text = event.eventName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = event.eventLocation,
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