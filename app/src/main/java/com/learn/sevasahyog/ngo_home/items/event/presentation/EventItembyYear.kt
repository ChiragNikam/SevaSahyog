package com.learn.sevasahyog.ngo_home.items.event.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.R


@Composable
fun EventItemByYear(appNavController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Event Name",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight(700)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Events Info",
                style = MaterialTheme.typography.labelLarge,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            ViewEventCard(
                eventName = "Event Name",
                byName = "by Name",
                location = "Location",
                shortDescription = "Techweek curates exciting programming that allows a global spotlight " ,
                imageUrls = listOf(
                    "https://via.placeholder.com/150",
                    "https://via.placeholder.com/150",
                    "https://via.placeholder.com/150",
                    "https://via.placeholder.com/150",
                    "https://via.placeholder.com/150",
                    "https://via.placeholder.com/150"
                )

            )
        }
    }
}


@Composable
fun ViewEventCard(eventName: String, byName: String, location: String, shortDescription: String,imageUrls:List<String>) {
    //Expanded state for image view
    var isExpandedCard by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
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
                text = byName,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = location,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
            Text(
                text = shortDescription,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 6.dp),
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Event Images",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = if (isExpandedCard) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpandedCard) "collapse" else "expand",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { isExpandedCard = !isExpandedCard }
                )
            }
            if (isExpandedCard) {
                Spacer(modifier = Modifier.height(10.dp))
                ImageGrid(imageUrls = imageUrls)
            }
        }
    }
}

@Composable
fun ImageGrid(imageUrls: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val rows = imageUrls.chunked(3)
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { imageUrl ->
                    ImageCard(imageUrl = imageUrl)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ImageCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .aspectRatio(2f),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bi_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}



@Preview
@Composable
private fun ViewEventItemByYearPreview() {
   EventItemByYear(appNavController = rememberNavController())
}