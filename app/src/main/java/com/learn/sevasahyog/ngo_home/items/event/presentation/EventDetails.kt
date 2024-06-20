package com.learn.sevasahyog.ngo_home.items.event.presentation

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.R
import com.learn.sevasahyog.common.CardInfoView
import com.learn.sevasahyog.common.DataViewInCard
import com.learn.sevasahyog.common.ExpandableInfoRow
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun EventDetailScreen(navController: NavController) {
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
                IconButton(onClick = { navController.navigateUp() }) {
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
            CardInfoView(label = "Event Details") {
                DataViewInCard(
                    info = "eventName",
                    infoDesc = "Event Name",
                    image = Icons.Default.AccountBox
                )

                Spacer(modifier = Modifier.height(10.dp))
                // event date
                DataViewInCard(info = "eventDate", infoDesc = "Date", image = Icons.Default.DateRange)

                Spacer(modifier = Modifier.height(10.dp))
                // event location
                DataViewInCard(info = "eventLocation", infoDesc = "Location", image = Icons.Default.LocationOn)

                Spacer(modifier = Modifier.height(10.dp))
                // event organizer name
                DataViewInCard(info = "eventOrganizer", infoDesc = "Organizer", image = Icons.Default.Person)

                Spacer(modifier = Modifier.height(10.dp))
                // organizer mobile number
                DataViewInCard(info = "mobile", infoDesc = "Mobile", image = Icons.Default.Phone)

                Spacer(modifier = Modifier.height(10.dp))
                // event long description
                ExpandableInfoRow(
                    text = "eventLongDescription",
                    expanded = isEventLongDescriptionExpanded,
                    onToggleExpand = { isEventLongDescriptionExpanded = !isEventLongDescriptionExpanded },
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
                        EventStatus(isLive = false)
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedButton(
                            onClick = { },
                        ) {
                            Text("Update")
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // upload image
            UploadImageBox()
        }
    }
}

@Composable
fun EventStatus(isLive: Boolean) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isLive) "Ongoing" else "Finished",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ellipse_5),
                contentDescription = null,
                tint = if (isLive) Color.Green else Color.Red
            )
        }
        Text(text = "Event Status", fontSize = MaterialTheme.typography.labelMedium.fontSize)
    }
}

@Composable
fun UploadImageBox() {
    val context = LocalContext.current
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            imageUris = uris
        }
    }

    Box(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .clickable { imagePickerLauncher.launch("image/*") },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bi_image),
                    contentDescription = "icons",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Upload Image",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentDescription = "upload Image Icon",
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            if (imageUris.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(imageUris.size) { index ->
                        val uri = imageUris[index]
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.White)
                                .padding(8.dp)
                        )
                    }
                }
            } else Icon(
                painter = painterResource(id = R.drawable.bi_image),
                contentDescription = "Upload Image",
            )

        }
    }
}

@Preview
@Composable
private fun PreviewUploadImageBox() {
    SevaSahyogTheme {
        UploadImageBox()
    }
}

@Preview
@Composable
private fun PreviewEventDetailScreen() {
    SevaSahyogTheme {
        EventDetailScreen(rememberNavController())
    }
}