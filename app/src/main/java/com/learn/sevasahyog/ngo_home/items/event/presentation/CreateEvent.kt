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
import com.learn.sevasahyog.ngo_home.items.event.domain.EventViewModel

@Composable
fun CreateEvent(
    appNavController: NavController,
    viewModel: EventViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(0.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Close Icon
            IconButton(onClick = { appNavController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Create Event",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Save Button
            Button(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            {
                Text("Save", fontSize = 16.sp)
            }
        }

        val event by viewModel.event.collectAsState()

        // Event Name
        LabeledTextField(
            value = event.name,
            onValueChange = { eventName ->
                viewModel.updateEvent { it.copy(name = eventName) }
            },
            label = "Event Name",
            leadingIcon = Icons.Default.Info
        )

        // Event Date
//            LabeledTextField(
//                value = event.dd ,
//                onValueChange = { eventDate },
//                label = "Event Date",
//                leadingIcon = Icons.Default.DateRange,
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.DateRange,
//                        contentDescription = "Calendar"
//                    )
//                }
//            )

        // Event Location
        LabeledTextField(
            value = event.location,
            onValueChange = { location ->
                viewModel.updateEvent { it.copy(location = location) }
            },
            label = "Event Location",
            leadingIcon = Icons.Default.Place
        )

        // Organizer Name
        LabeledTextField(
            value = event.organizer,
            onValueChange = { organizer ->
                viewModel.updateEvent { it.copy(organizer = organizer) }
            },
            label = "Organizer Name",
            leadingIcon = Icons.Default.Person
        )

        // Organizer Mobile No.
        LabeledTextField(
            value = event.organizerPhone,
            onValueChange = { phoneNo ->
                viewModel.updateEvent { it.copy(organizerPhone = phoneNo) }
            },
            label = "Organizer Mobile No.",
            leadingIcon = Icons.Default.Phone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        // Short Description
        LabeledTextField(
            value = event.shortDesc,
            onValueChange = { shortDesc ->
                viewModel.updateEvent { it.copy(shortDesc = shortDesc) }
            },
            label = "Short Description",
            leadingIcon = Icons.Default.Create,
            maxLines = 2
        )

        // Long Description
        LabeledTextField(
            value = event.longDesc,
            onValueChange = { longDesc ->
                viewModel.updateEvent { it.copy(longDesc = longDesc) }
            },
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