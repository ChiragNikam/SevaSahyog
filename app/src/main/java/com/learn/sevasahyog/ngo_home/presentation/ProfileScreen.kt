package com.learn.sevasahyog.ngo_home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.R
import com.learn.sevasahyog.common.CardInfoView
import com.learn.sevasahyog.common.DataViewInCard
import com.learn.sevasahyog.ngo_home.domain.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = "Profile",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            UserProfileImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(70.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                // user info
                val userName by viewModel.userName.collectAsState()
                val phoneNumber by viewModel.phoneNumber.collectAsState()
                val userEmail by viewModel.email.collectAsState()
                CardInfoView(label = "User Info") {
                    DataViewInCard(info = userName, infoDesc = "User Name", image = Icons.Filled.Face)

                    Spacer(modifier = Modifier.height(12.dp))

                    DataViewInCard(info = phoneNumber, infoDesc = "Mobile", image = Icons.Filled.Phone)

                    Spacer(modifier = Modifier.height(12.dp))

                    DataViewInCard(info = userEmail, infoDesc = "E-mail", image = Icons.Filled.Email)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ngo info
                val ngoName by viewModel.ngoName.collectAsState()
                val ngoLocation by viewModel.ngoLocation.collectAsState()
                val aboutNgo by viewModel.ngoInfo.collectAsState()
                val ngoDescription by viewModel.ngoLongDescription.collectAsState()
                var isAboutNgoExpanded by remember { mutableStateOf(false) }
                var isNgoDescriptionExpanded by remember { mutableStateOf(false) }
                CardInfoView(label = "Ngo Info") {
                    DataViewInCard(info = ngoName, infoDesc = "Ngo Name", image = Icons.Filled.Home)

                    Spacer(modifier = Modifier.height(12.dp))

                    DataViewInCard(
                        info = ngoLocation,
                        infoDesc = "Location",
                        image = Icons.Filled.LocationOn
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    ExpandableInfoRow(
                        text = aboutNgo,
                        expanded = isAboutNgoExpanded,
                        onToggleExpand = { isAboutNgoExpanded = !isAboutNgoExpanded },
                        imageVector = Icons.Default.Info
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    ExpandableInfoRow(
                        text = ngoDescription,
                        expanded = isNgoDescriptionExpanded,
                        onToggleExpand = { isNgoDescriptionExpanded = !isNgoDescriptionExpanded },
                        imageVector = Icons.AutoMirrored.Filled.List
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                        disabledContainerColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sign out", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "sign out")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun UserProfileImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Cover image",
            modifier = Modifier
                .padding(bottom = 70.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "edit",
                modifier = Modifier.size(32.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
//                    .offset(y = 60.dp)  // Adjust offset to position profile image correctly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconamoon_profile_fill),
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center),
                )
                IconButton(
                    onClick = { /* Add icon click handler */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-10).dp, y = (-10).dp)
                        .size(36.dp)
//                        .background(MaterialTheme.colorScheme.background, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Add icon",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableInfoRow(
    text: String,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    imageVector: ImageVector
) {
    Column {
        Row {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable(onClick = onToggleExpand),
                maxLines = 2,
                overflow = TextOverflow.Visible
            )
        }
        if (text.length > 100) {
            Text(
                text = if (expanded) "Read less" else "Read more",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSecondaryContainer),
                modifier = Modifier
                    .clickable(onClick = onToggleExpand)
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End)
            )
        }
    }
}


@Preview
@Composable
private fun PreviewProfileScreen() {
    ProfileScreen(navController = rememberNavController())
}
