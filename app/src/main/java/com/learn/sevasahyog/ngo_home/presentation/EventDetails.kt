package com.learn.sevasahyog.ngo_home.presentation

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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learn.sevasahyog.R
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme

@Composable
fun EventDetailScreen(){
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
            Text(
                text = "Event's Detail",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight(700)
            )
            Image(painter = painterResource(id = R.drawable.img), contentDescription = "image", modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally) )

            Spacer(modifier = Modifier.height(10.dp))
            EvenDetails(
                eventName = "Name of Event",
                eventDate = "16 Dec 2023",
                eventLocation ="Manavada ,Nagpur" ,
                eventOrganizer = "Chirag Nikam",
                leadMobile = "+91 7878900324",
                eventLongDescription ="This section is about Ngo.It will have short description and " + "and the moto of the Ngo, ngoDescription. It is a long established fact that a reader " +
                        "will be distracted by readable content of page when there is no hope of " +
                        "meaning for the words."
            )
            Spacer(modifier = Modifier.height(32.dp))
            StatusCard()
            Spacer(modifier = Modifier.height(12.dp))
            UploadImageBox()
        }
    }

}


@Composable
fun EvenDetails(eventName: String, eventDate: String, eventLocation: String, eventOrganizer: String,leadMobile:String,eventLongDescription:String) {
    var isEventLongDescriptionExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF9FFF3))
                .padding(16.dp)
        ) {
            Text(
                text = "Event Details",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(10.dp))
            EventTextInfo(
                text = eventDate,
                imageVector = Icons.Default.DateRange
            )
            Spacer(modifier = Modifier.height(10.dp))
            EventTextInfo(
                text = eventLocation,
                imageVector = Icons.Default.Place
            )
            Spacer(modifier = Modifier.height(10.dp))
            EventTextInfo(
                text = eventOrganizer,
                imageVector = Icons.Default.Person
            )
            Spacer(modifier = Modifier.height(10.dp))
            EventTextInfo(
                text = leadMobile,
                imageVector = Icons.Default.Call
            )
            Spacer(modifier = Modifier.height(10.dp))
            ExpandableEventInfo(
                text = eventLongDescription,
                expanded = isEventLongDescriptionExpanded,
                onToggleExpand = { isEventLongDescriptionExpanded = !isEventLongDescriptionExpanded },
                imageVector = Icons.Default.List
            )

        }

    }
}

@Composable
fun EventTextInfo(text: String, imageVector: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector=imageVector,
            contentDescription =null,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun ExpandableEventInfo(text: String, expanded: Boolean, onToggleExpand: () -> Unit, imageVector: ImageVector) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector=imageVector,
                contentDescription =null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (expanded) text else text.take(100) + if (text.length > 100) "..." else "",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable(onClick = onToggleExpand)
            )
        }
        if (text.length > 100) {
            Text(
                text = if (expanded) "Read less" else "Read more",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Blue),
                modifier = Modifier
                    .clickable(onClick = onToggleExpand)
                    .align(Alignment.End)
            )
        }
    }
}

@Composable
fun StatusCard() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF9FFF3))
                .padding(16.dp)
        ) {
            Text(
                text = "Status",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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
                    EventStatus(isLive = true)
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { },
                    ) {
                        Text("Update")
                    }

                }
            }

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
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ellipse_5),
                contentDescription = null,
                tint = if (isLive) Color.Green else Color.Red
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Event Status")
    }
}

@Composable
fun UploadImageBox() {
    val context = LocalContext.current
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val imagePickerLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()) {
            uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            imageUris = uris
        }
    }



    Box(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Row (modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
                horizontalArrangement = Arrangement.Center
            ){
                Icon(
                    painter = painterResource(id = R.drawable.bi_image),
                    contentDescription = "icons",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Upload Image",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 12.dp),
                    textAlign = TextAlign.Center
                    )
                Spacer(modifier = Modifier.padding(60.dp))
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    modifier = Modifier
                        .padding(top = 12.dp, end = 6.dp)
                        .fillMaxWidth()
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        },
                    contentDescription = "upload Image Icon",)
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
                tint = Color.Black
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
        EventDetailScreen()
    }

}