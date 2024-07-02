package com.learn.sevasahyog.ngo_home.items.profile.presentation

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.R
import com.learn.sevasahyog.auth.common.CheckInternetConnectionView
import com.learn.sevasahyog.auth.common.ShimmerListItem
import com.learn.sevasahyog.auth.common.shimmerEffect
import com.learn.sevasahyog.auth.domain.SessionManager
import com.learn.sevasahyog.common.CardInfoView
import com.learn.sevasahyog.common.DataViewInCard
import com.learn.sevasahyog.common.ExpandableInfoRow
import com.learn.sevasahyog.common.ImageLoadDialogView
import com.learn.sevasahyog.common.LoadImageFromUrl
import com.learn.sevasahyog.ngo_home.items.profile.domain.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    appNavController: NavController,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // do network request for the user profile and load it to view-model
    viewModel.loadProfile()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        val context = LocalContext.current
        val configuration = LocalConfiguration.current

        val session =
            SessionManager(context)   // session to get the user details stored in shared-preference
        val data = session.getUserDetails()
        // set token and uid to view-model for network requests
        data["token"]?.let { viewModel.updateAccessToken(it) }
        data["uid"]?.let { viewModel.updateUserId(it) }

        val internetConnection by viewModel.internetConnection.collectAsState()

        if (!internetConnection) Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CheckInternetConnectionView(
                modifier = Modifier.fillMaxSize(), imageModifier = Modifier.size(150.dp)
            )
            Button(onClick = { viewModel.loadProfile() }) {
                Text(text = "Refresh")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh")
            }
        }

        if (internetConnection) Column {
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Profile",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        fontWeight = FontWeight(700)
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))
            }
            val userProfileProgress by viewModel.userProfileProgress.collectAsState()
            if (userProfileProgress) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

            // User Profile and Ngo Image
            UserProfileImage(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
            )

            Spacer(modifier = Modifier.height(70.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                val profile by viewModel.profile.collectAsState()
                // user info
                val userName = profile.userName
                val mobileNo = profile.mobileNo
                val email = profile.email
                CardInfoView(label = "User Info") {
                    // user name
                    ShimmerListItem(isLoading = userProfileProgress, contentBeforeLoading = {
                        ContentBeforeLoading()
                    }) {
                        DataViewInCard(
                            info = userName, infoDesc = "User Name", image = Icons.Filled.Face
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // phone no
                    ShimmerListItem(isLoading = userProfileProgress,
                        contentBeforeLoading = { ContentBeforeLoading() }) {
                        DataViewInCard(
                            info = mobileNo, infoDesc = "Mobile", image = Icons.Filled.Phone
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // email
                    ShimmerListItem(isLoading = userProfileProgress,
                        contentBeforeLoading = { ContentBeforeLoading() }) {
                        DataViewInCard(
                            info = email, infoDesc = "E-mail", image = Icons.Filled.Email
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ngo info
                val ngoName = profile.ngoName
                val ngoLocation = profile.location
                val aboutNgo = profile.aboutNgo
                val ngoDescription = profile.longDesc
                var isAboutNgoExpanded by remember { mutableStateOf(false) }
                var isNgoDescriptionExpanded by remember { mutableStateOf(false) }
                CardInfoView(label = "Ngo Info") {

                    // ngo name
                    ShimmerListItem(isLoading = userProfileProgress,
                        contentBeforeLoading = { ContentBeforeLoading() }) {
                        DataViewInCard(
                            info = ngoName, infoDesc = "Ngo Name", image = Icons.Filled.Home
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // location
                    ShimmerListItem(isLoading = userProfileProgress,
                        contentBeforeLoading = { ContentBeforeLoading() }) {
                        DataViewInCard(
                            info = ngoLocation,
                            infoDesc = "Location",
                            image = Icons.Filled.LocationOn
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // about ngo
                    ShimmerListItem(isLoading = userProfileProgress,
                        contentBeforeLoading = { ContentBeforeLoading() }) {
                        ExpandableInfoRow(
                            text = aboutNgo,
                            expanded = isAboutNgoExpanded,
                            onToggleExpand = { isAboutNgoExpanded = !isAboutNgoExpanded },
                            imageVector = Icons.Default.Info
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // long description
                    ShimmerListItem(isLoading = userProfileProgress,
                        contentBeforeLoading = { ContentBeforeLoading() }) {
                        ExpandableInfoRow(
                            text = ngoDescription,
                            expanded = isNgoDescriptionExpanded,
                            onToggleExpand = {
                                isNgoDescriptionExpanded = !isNgoDescriptionExpanded
                            },
                            imageVector = Icons.AutoMirrored.Filled.List
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        session.logoutUser()
                        // pop complete ngo navigation from the nav graph and start auth
                        appNavController.popBackStack("ngo", true, saveState = false)
                        appNavController.navigate("auth")
                    },
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
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "sign out"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

    }
}

@Composable
fun UserProfileImage(modifier: Modifier = Modifier, viewModel: ProfileViewModel) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val imageType = rememberSaveable {   // P: Profile, B: Background
        mutableStateOf("")
    }

    val showPreviewDialog = rememberSaveable {
        mutableStateOf(false)
    }

    val localBackgroundUri = rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val localProfileUri = rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncherBackground = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            localBackgroundUri.value = uri
            showPreviewDialog.value = true
        }
    }

    val lifecycle = rememberCoroutineScope()

    val imagePickerLauncherProfile = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            lifecycle.launch {
                localProfileUri.value = uri
                showPreviewDialog.value = true
            }
        }
    }

    // dialog for preview of background image
    if (showPreviewDialog.value)
        ImageLoadDialogView(
            onDismissRequest = { showPreviewDialog.value = false },
            onConfirmation = {
                lifecycle.launch {
                    (if (imageType.value == "B") localBackgroundUri.value else localProfileUri.value)?.let {
                        viewModel.uploadImageToFirebase(
                            uri = it,
                            imageType.value,
                            onSuccess = { url ->
                                // Handle success, e.g., update the viewModel with the download URL
                                if (imageType.value == "B") {
                                    viewModel.updateBackgroundImageUrl(url)
                                } else {
                                    viewModel.updateProfilePicUrl(url)
                                }
                                viewModel.updatePics()
                                viewModel.loadProfile()
                            },
                            onFailure = { e ->
                                // Handle failure
                                Log.e("Firebase", "Failed to upload image", e)
                            }
                        )
                    }
                    showPreviewDialog.value = false
                }
            },
            dialogTitle = "",
            dialogText = "",
            image = if (imageType.value == "B") localBackgroundUri.value else localProfileUri.value
        )

    Box(
        modifier = modifier
    ) {
        // background image
        val ngoAccount by viewModel.profile.collectAsState()
        val backgroundPic by viewModel.backgroundImageUrl.collectAsState()
        LoadImageFromUrl(
            modifier = Modifier
                .padding(bottom = 70.dp)
                .fillMaxSize(),
            url = if (backgroundPic != null) backgroundPic else ngoAccount.ngoImage,
            placeholderPainter = painterResource(id = R.drawable.ic_launcher_background),
            errorPainter = painterResource(id = R.drawable.ic_launcher_background)
        )

        IconButton( // to select background image
            onClick = {
                imagePickerLauncherBackground.launch("image/*")
                imageType.value = "B"
            },
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
        val profilePic by viewModel.profilePicUrl.collectAsState()
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .size(144.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            ) {
                // profile image
                LoadImageFromUrl(
                    modifier = Modifier.fillMaxSize().padding(4.dp).clip(CircleShape),
                    url = if (profilePic != null) profilePic else ngoAccount.profileImage,
                    placeholderPainter = painterResource(id = R.drawable.iconamoon_profile_fill),
                    errorPainter = painterResource(id = R.drawable.iconamoon_profile_fill)
                )
            }

            IconButton( // to select profile image: open image picker
                onClick = {
                    imagePickerLauncherProfile.launch("image/*")
                    imageType.value = "P"
                },
                modifier = Modifier
                    .padding(
                        end = if (configuration.orientation == 1) (configuration.screenWidthDp / 3 - 4).dp else (configuration.screenWidthDp / 3 + 62).dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.BottomEnd)
                    .size(36.dp)
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

@Composable
fun ContentBeforeLoading(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(12.dp))
    Row {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .height(20.dp)
                    .shimmerEffect()
            ) {}
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .height(12.dp)
                    .shimmerEffect()
            ) {}
        }
    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    ProfileScreen(
        appNavController = rememberNavController()
    )
}
