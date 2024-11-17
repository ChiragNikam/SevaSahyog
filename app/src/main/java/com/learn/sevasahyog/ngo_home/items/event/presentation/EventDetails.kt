package com.learn.sevasahyog.ngo_home.items.event.presentation

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.learn.sevasahyog.R
import com.learn.sevasahyog.auth.domain.SessionManager
import com.learn.sevasahyog.common.CardInfoView
import com.learn.sevasahyog.common.DataViewInCard
import com.learn.sevasahyog.common.ExpandableInfoRow
import com.learn.sevasahyog.common.LoadImageFromUrl
import com.learn.sevasahyog.ngo_home.items.event.domain.CreateEventViewModel
import com.learn.sevasahyog.ngo_home.items.event.domain.EventsViewModel
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventDetailScreen(
    navController: NavController,
    viewModel: EventsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    eventId: Long
) {
    val context = LocalContext.current
    val session =
        SessionManager(context)   // session to get the user details stored in shared-preference
    val data = session.getUserDetails()
    // set token and uid to view-model for network requests
    data["token"]?.let {
        viewModel.updateAccessToken(it)
    }
    data["uid"]?.let {
        viewModel.updateUserId(it)
    }

    val eventData by viewModel.eventResponse.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("event_id", eventId.toString())
        viewModel.getEventByItsId(eventId)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Event's Detail",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight(700)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "image",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // event details
            var isEventLongDescriptionExpanded by remember { mutableStateOf(false) }
            var isEventShortDescExpanded by remember { mutableStateOf(false) }
            CardInfoView(label = "Event Details") {
                // event name
                DataViewInCard(
                    info = eventData.name,
                    infoDesc = "Event Name",
                    image = Icons.Default.AccountBox
                )

                Spacer(modifier = Modifier.height(10.dp))
                // event date
                DataViewInCard(
                    info = "${eventData.dd}/${eventData.mm}/${eventData.yyyy}",
                    infoDesc = "Date",
                    image = Icons.Default.DateRange
                )

                Spacer(modifier = Modifier.height(10.dp))
                // event location
                DataViewInCard(
                    info = eventData.location,
                    infoDesc = "Location",
                    image = Icons.Default.LocationOn
                )

                Spacer(modifier = Modifier.height(10.dp))
                // event organizer name
                DataViewInCard(
                    info = eventData.organizer,
                    infoDesc = "Organizer",
                    image = Icons.Default.Person
                )

                Spacer(modifier = Modifier.height(10.dp))
                // organizer mobile number
                DataViewInCard(
                    info = eventData.organizerPhone,
                    infoDesc = "Mobile",
                    image = Icons.Default.Phone
                )

                Spacer(modifier = Modifier.height(10.dp))
                // event short description
                ExpandableInfoRow(
                    text = eventData.shortDesc,
                    expanded = isEventShortDescExpanded,
                    onToggleExpand = {
                        isEventShortDescExpanded = !isEventShortDescExpanded
                    },
                    imageVector = Icons.Default.Info
                )
                // event long description
                ExpandableInfoRow(
                    text = eventData.longDesc,
                    expanded = isEventLongDescriptionExpanded,
                    onToggleExpand = {
                        isEventLongDescriptionExpanded = !isEventLongDescriptionExpanded
                    },
                    imageVector = Icons.AutoMirrored.Filled.List
                )

            }

            Spacer(modifier = Modifier.height(32.dp))

            // status
            CardInfoView(label = "Status") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.vector__1_),
                            contentDescription = "liveCheck"
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // event status
                        EventStatus(eventStatus = eventData.status)

                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedButton(
                            onClick = { },
                        ) {
                            Text("Update")
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            val eventImagesUrls by viewModel.eventImagesUrls.collectAsState()

            val pagerState = rememberPagerState(
                pageCount = { eventData.eventImagesUrls?.size ?: 0 }
            )
            if (eventData.eventImagesUrls?.isNotEmpty() == true) {
                val eventImages = eventData.eventImagesUrls
                HorizontalPager(
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth(),
                    state = pagerState,
                    pageSpacing = 12.dp
                ) { page ->
//                    if (page == eventData.eventImagesUrls!!.size) {
//                        // icon to add images
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Box(
//                                modifier = Modifier.background(
//                                    color = MaterialTheme.colorScheme.secondary,
//                                    shape = CircleShape
//                                )
//                            ) {
//                                IconButton(onClick = { /*TODO*/ }) {
//                                    Icon(
//                                        imageVector = Icons.Default.Add,
//                                        contentDescription = "Add Images",
//                                        tint = MaterialTheme.colorScheme.onSecondary
//                                    )
//                                }
//                            }
//                        }
//                    } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LoadImageFromUrl(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            url = eventImages?.get(page),
                            placeholderPainter = painterResource(id = R.drawable.ic_launcher_background),
                            errorPainter = painterResource(id = R.drawable.ic_launcher_background)
                        )
                    }
//                    }
                }
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add image",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            } else {
                // upload image
                UploadImageBox(viewModel = viewModel, eventId = eventId)
            }
            Spacer(modifier = Modifier.height(62.dp))
        }
    }
}

@Composable
fun EventStatus(eventStatus: Int) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (eventStatus) {
                    0 -> "Past"
                    1 -> "Ongoing"
                    2 -> "Upcoming"
                    else -> "NULL"
                },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ellipse_5),
                contentDescription = null,
                tint = if (eventStatus == 1) Color.Green else if (eventStatus == 2) Color.Red else Color.Blue,
            )
        }
        Text(text = "Event Status", fontSize = MaterialTheme.typography.labelMedium.fontSize)
    }
}

@Composable
fun UploadImageBox(viewModel: EventsViewModel, eventId: Long) {
    val context = LocalContext.current
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope for the composable

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            imageUris = uris
            coroutineScope.launch { // Launch a coroutine to call the suspend function
                try {
                    viewModel.uploadImagesToFirebase(
                        uris = uris,
                        eventId = eventId.toString(),
                        onSuccess = { urls ->
                            viewModel.updateEventImagesUrlByAPI(urls, eventId)
                            Log.d("ImageUpload", "Images uploaded successfully: $urls")
                        },
                        onFailure = { exception ->
                            Log.e("ImageUpload", "Error uploading image: ${exception.message}")
                        }
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Failed to Upload images. Please retry!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("ImageUpload", "Error in coroutine: ${e.message}")
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    imagePickerLauncher.launch("image/*")
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bi_image),
                contentDescription = "Upload Icon",
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Upload Image", fontSize = 18.sp, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}


@Preview
@Composable
private fun PreviewUploadImageBox() {
    SevaSahyogTheme {
        UploadImageBox(viewModel = EventsViewModel(), eventId = 123)
    }
}

@Preview
@Composable
private fun PreviewEventDetailScreen() {
    SevaSahyogTheme {
        EventDetailScreen(rememberNavController(), eventId = 56)
    }
}