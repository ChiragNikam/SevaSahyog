package com.learn.sevasahyog.common

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.learn.sevasahyog.ui.theme.SevaSahyogTheme
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun DataViewInCard(
    modifier: Modifier = Modifier,
    info: String,
    infoDesc: String,
    image: ImageVector,
    contentDesc: String = ""
) {
    Row(modifier = modifier) {
        Icon(modifier = Modifier.size(24.dp), imageVector = image, contentDescription = contentDesc)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = info,
                fontSize = 18.sp
            )
            Text(text = infoDesc, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun CardInfoView(
    modifier: Modifier = Modifier,
    label: String,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

            content()
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
                maxLines = if (!expanded) 2 else 12,
                overflow = TextOverflow.Ellipsis
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

@Composable
fun AlertDialogView(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Dialog Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun ImageLoadDialogView(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    image: Uri?,
) {
    val context = LocalContext.current

    val inputStreamImage = image?.let { context.contentResolver.openInputStream(it) }
    val bitmapImage = BitmapFactory.decodeStream(inputStreamImage)
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background, RoundedCornerShape(18.dp))
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(0.5f)),
                bitmap = bitmapImage.asImageBitmap(),
                contentDescription = "Image Preview",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {

                OutlinedButton(onClick = { onDismissRequest() }) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = {
                    onConfirmation()
                }) {
                    Text(text = "Update")
                }
            }
        }
    }
}

@Preview
@Composable
private fun DialogPreview() {
    SevaSahyogTheme {
        AlertDialogView(
            onDismissRequest = { /*TODO*/ },
            onConfirmation = { /*TODO*/ },
            dialogTitle = "Alert",
            dialogText = "Are you sure that you want to dismiss. If you do so you will not be able to save the event.",
            icon = Icons.Filled.Build
        )
    }
}

@Preview
@Composable
private fun DialogImagePreview() {
    SevaSahyogTheme {
        ImageLoadDialogView(
            onDismissRequest = { /*TODO*/ },
            onConfirmation = { /*TODO*/ },
            dialogTitle = "Alert",
            dialogText = "Are you sure that you want to dismiss. If you do so you will not be able to save the event.",
            image = null
        )
    }
}

@Preview
@Composable
private fun DataViewInCardPrev() {
    DataViewInCard(image = Icons.Filled.Face, info = "Chirag Nikam", infoDesc = "Name")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SevaSehyogDatePicker(
    onDismissRequest: () -> Unit,
    selectedDate: MutableState<String>,
    dateFormat: SimpleDateFormat,
    selectedDateValue: Date
): String {
    val datePickerState = rememberDatePickerState()

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                DatePicker(
                    state = datePickerState,
                    title = {
                        Text("Select a date")
                    },
                    showModeToggle = true,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                           selectedDate.value = dateFormat.format(
                                Date(
                                    datePickerState.selectedDateMillis ?: selectedDateValue.time
                                )
                            )
                            Log.d("date", "newSelected date : ${selectedDate.value}")
                            onDismissRequest()

                        }
                    ) {
                        Text("Ok")
                    }
                }
            }
        }
    }

    return selectedDate.value
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

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines
        )

    }
}

fun getDateComponents(dateString: String): Triple<Int, Int, Int> {
    // Split the date string by the separator "/"
    val dateParts = dateString.split("/")
    // Extract the year, month, and day from the split parts
    val year = dateParts[2].toInt()
    val month = dateParts[1].toInt()
    val day = dateParts[0].toInt()
    // Return the components as a Triple
    return Triple(day, month, year)
}
