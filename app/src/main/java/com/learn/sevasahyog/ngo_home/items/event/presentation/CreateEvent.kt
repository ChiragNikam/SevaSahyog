package com.learn.sevasahyog.ngo_home.items.event.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learn.sevasahyog.auth.domain.SessionManager
import com.learn.sevasahyog.common.LabeledTextField
import com.learn.sevasahyog.common.SevaSehyogDatePicker
import com.learn.sevasahyog.common.getDateComponents
import com.learn.sevasahyog.ngo_home.items.event.domain.EventViewModel
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEvent(
    appNavController: NavController,
    viewModel: EventViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current

    val session = SessionManager(context)
    val data = session.getUserDetails()
    data["email"]?.let { viewModel.updateEmail(it) }    // set the email for create event request
    data["token"]?.let { viewModel.updateAccessToken(it) }  // set access token

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Close Icon
                IconButton(onClick = {
                    appNavController.navigateUp()
                    viewModel.clearData()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Create Event",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Save Button
                Button(
                    onClick = {
                        viewModel.createEvent()
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Save", fontSize = 16.sp)
                }
            }
        }
    ) {
        val savedEventSuccess by viewModel.saveEventSuccess.collectAsState()
        if (savedEventSuccess) {
            Toast.makeText(context, "Event Saved successfully", Toast.LENGTH_SHORT).show()
            viewModel.updateSaveEventSuccess(false)
            appNavController.navigateUp()
            appNavController.navigate("event/eventDetailScreen")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
            var datePickerVisible by remember { mutableStateOf(false) }
            val selectedDate = remember { mutableStateOf("") }
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDateValue by remember { mutableStateOf(java.util.Date()) }

            // set current date to selected date
            if (selectedDate.value == "")
                selectedDate.value = dateFormat.format(
                    java.util.Date(
                        rememberDatePickerState().selectedDateMillis ?: selectedDateValue.time
                    )
                )

            //  Event Date
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "select date")
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    readOnly = true,
                    value = selectedDate.value,
                    onValueChange = {},
                    label = { Text(text = "Event Date") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar",
                            modifier = Modifier
                                .size(38.dp)
                                .clickable { datePickerVisible = true },
                        )
                    }
                )
            }

            if (datePickerVisible)
                selectedDate.value = SevaSehyogDatePicker(
                    onDismissRequest = {
                        Log.d("selectedDate", selectedDate.value)
                        datePickerVisible = false
                    },
                    selectedDate = selectedDate,
                    dateFormat = dateFormat,
                    selectedDateValue = selectedDateValue
                )

            val (day, month, year) = getDateComponents(selectedDate.value)  // parse event date sting and get date, month and year out of it
            viewModel.updateEvent {
                it.copy(dd = day, mm = month, yyyy = year)
            }

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
                onValueChange = { organizerPhone ->
                    viewModel.updateEvent {
                        it.copy(organizerPhone = organizerPhone)
                    }
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
                leadingIcon = Icons.AutoMirrored.Filled.List,
                maxLines = 5
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCreateEvent() {
    CreateEvent(appNavController = rememberNavController())
}