package com.learn.sevasahyog.ngo_home.items.all.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.learn.sevasahyog.R
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.draw.clip
import com.learn.sevasahyog.ngo_home.items.event.data.Member


@Composable
fun AllScreen(navController: NavController) {
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
                text = "All",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight(700)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "TODAY", style = MaterialTheme.typography.labelLarge, letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            EventCard(
                eventName = "Event Name",
                byName = "by Name",
                location = "Location",
                description = "Techweek curates exciting programming that allows a global spotlight " + "to shine on each ecosystem and its leaders. Past speakers include Rahm Emanuel, " + "Travis Kalanick (CEO, Uber), Craig Newmark (Founder, Craigslist)," + " Barney Harford (CEO, Orbitz), and Chuck Templeton (Founder, OpenTable)"
            )

            Spacer(modifier = Modifier.height(32.dp))


            NGOIntroductionCard(
                ngoName = "SevaSahyog",
                introduction = "SevaSahyog is a platform where we all stand together for the betterment of our entrepreneurial " + "establishments along with social welfare and upkeep of people in general. Here we all stand " + "hand in hand to reach our desired goals, because 'Alone we can do so little, together we can do so much.'",
//                imageUrl = "your_image_url_here"
            )

            Spacer(modifier = Modifier.height(32.dp))

            FounderMessageLayout()

            Spacer(modifier = Modifier.height(32.dp))

            MemberGridLayout()

        }
    }
}

@Composable
fun MemberGridLayout() {
    val members = listOf(
        Member(R.drawable.founder, "Rajat", "President"),
        Member(R.drawable.founder, "Chirag", "Vice President"),
        Member(R.drawable.founder, "Rahul", "Secretary"),
        Member(R.drawable.founder, "Om", "Treasurer"),
        Member(R.drawable.founder, "Himanshu", "Volunteer"),
        Member(R.drawable.founder, "Vedansh", "Member")
    )

    // Creating a grid layout manually using Row and Column
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        for (i in members.indices step 3) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in 0 until 3) {
                    if (i + j < members.size) {
                        MemberView(members[i + j])
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Space between rows
        }
    }
}


@Composable
fun MemberView(member: Member) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(100.dp)
    ) {
        // Member Image
        Image(
            painter = painterResource(id = member.imageResId), // Replace with actual image resource
            contentDescription = member.name,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape), // Circular Image
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = member.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black
        )

        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = member.post, fontSize = 12.sp, color = Color.Gray
        )
    }
}

@Composable
fun NGOIntroductionCard(ngoName: String, introduction: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),

        ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFFCE1B2))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Introduction",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.seva),
                contentDescription = "NGO Introduction Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = ngoName,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            Text(
                text = introduction,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 6.dp),
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF4A12E)) // Orange background color
                    .padding(vertical = 16.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Salient Features",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Center text
            Text(
                text = "One Voice For All",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "A voice for those with similar needs and concerns",
                fontSize = 10.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                FeatureCard(
                    title = "Networking Opportunities",
                    description = "Opportunity to network, share information & resources"
                )
                FeatureCard(
                    title = "Social Advantages",
                    description = "Opportunity to know people outside one's professional spectrum"
                )
            }


            // Circle Image
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Green, shape = RoundedCornerShape(100.dp)), // Green border
                contentAlignment = Alignment.Center
            ) {
                Image(
                    // painter = rememberAsyncImagePainter(model = "file:///mnt/data/Screenshot 2024-09-08 at 12.25.09 AM.png"),
                    painter = painterResource(id = R.drawable.holdinghand),
                    contentDescription = "NGO Image",
                    modifier = Modifier.size(100.dp), // Image size inside circle
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                FeatureCard(
                    title = "Social Advocacy",
                    description = "Working on political issues affecting our members"
                )
                FeatureCard(
                    title = "Improved Profitability",
                    description = "To create an atmosphere that creates and enhances profitability"
                )
            }
        }
    }

}

@Composable
fun FounderMessageLayout() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Left Section: Image of the Founder
        Image(
            painter = painterResource(id = R.drawable.founder), // replace with your image resource
            contentDescription = "Founder Image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))

        // Right Section: Text (Message from the Founder)
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Founder's Message",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C4DB2) // Adjust color as per the design
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Jai Shri Ram,",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722) // Orange color as shown in the image
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "I hope this message finds you all well and thriving. As we navigate through another exciting year at Business and Services Association (Regd.), I wanted to take a moment to connect with each and every one of you.\n\n" + "Firstly, let me express my gratitude for the incredible enthusiasm and participation we've witnessed in our recent events and activities. Your passion for fostering a vibrant and inclusive community is truly inspiring, and it's the driving force behind the success of our Association.\n\n",
                fontSize = 12.sp,
                color = Color.Black,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun FeatureCard(title: String, description: String) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = Color.Black,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    lineHeight = 16.sp
        )
    }
}

@Composable
fun EventCard(eventName: String, byName: String, location: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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
                text = description,
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
                    text = "Upload Image",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    SevaSahyogTheme {
        AllScreen(navController = rememberNavController())
    }
}