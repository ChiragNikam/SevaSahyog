package com.learn.sevasahyog.ngo_home.items.event.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.ngo_home.items.event.domain.CreateEventViewModel

@Composable
fun CreateEvent(
    appNavController: NavController,
    viewModel: CreateEventViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Close Icon
                IconButton(onClick = { appNavController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(32.dp)
                    )
                }

                // Spacer to push the title to the center
                Spacer(modifier = Modifier.weight(1f))

                // Title
                Text(
                    text = "Create Event",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                // Spacer to push the save button to the end
                Spacer(modifier = Modifier.weight(1f))

                // Save Button
                Button(
                    onClick = { },
                    modifier = Modifier.align(Alignment.CenterVertically),

                    ) {
                    Text("Save", fontSize = 16.sp)
                }
            }
            val eventName by viewModel.eventName.collectAsState()
            val eventDate by viewModel.eventDate.collectAsState()
            val eventLocation by viewModel.eventLocation.collectAsState()
            val eventOrganizerName by viewModel.eventOrganizerName.collectAsState()
            val organizerMobileNo by viewModel.organizerMobileNo.collectAsState()
            val eventShortDesc by viewModel.eventShortDesc.collectAsState()
            val eventLongDesc by viewModel.eventLongDesc.collectAsState()
            // Event Name
            LabeledTextField(
                value = eventName,
                onValueChange = { eventName },
                label = "Event Name",
                leadingIcon = Icons.Default.Info
            )

            // Event Date
            LabeledTextField(
                value = eventDate,
                onValueChange = { eventDate },
                label = "Event Date",
                leadingIcon = Icons.Default.DateRange,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar"
                    )
                }
            )

            // Event Location
            LabeledTextField(
                value = eventLocation,
                onValueChange = { eventLocation },
                label = "Event Location",
                leadingIcon = Icons.Default.Place
            )

            // Organizer Name
            LabeledTextField(
                value = eventOrganizerName,
                onValueChange = { eventOrganizerName },
                label = "Organizer Name",
                leadingIcon = Icons.Default.Person
            )

            // Organizer Mobile No.
            LabeledTextField(
                value = organizerMobileNo,
                onValueChange = { organizerMobileNo },
                label = "Organizer Mobile No.",
                leadingIcon = Icons.Default.Phone,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            // Short Description
            LabeledTextField(
                value = eventShortDesc,
                onValueChange = { eventShortDesc },
                label = "Short Description",
                leadingIcon = Icons.Default.Create,
                maxLines = 2
            )

            // Long Description
            LabeledTextField(
                value = eventLongDesc,
                onValueChange = { eventLongDesc },
                label = "Long Description",
                leadingIcon = Icons.Default.List,
                maxLines = 5
            )

        }
    }



@Composable
fun LabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )
        CommonOutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CommonOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines
    )
}


@Preview
@Composable
private fun PreviewCreateEvent() {
    CreateEvent(appNavController = rememberNavController())
}