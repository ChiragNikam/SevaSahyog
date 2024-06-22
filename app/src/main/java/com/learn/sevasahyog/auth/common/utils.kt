package com.learn.sevasahyog.auth.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.learn.sevasahyog.R

@Composable
fun ValidatedTextField(
    textFieldModifier: Modifier = Modifier.fillMaxWidth(),
    textModifier: Modifier = Modifier,
    label: String,
    value: String,
    error: Boolean,
    errorMessage: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = textFieldModifier,
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(label) },
            singleLine = true,
            isError = error,
            keyboardOptions = keyboardOptions
        )
        if (error) {    // error message of email
            Text(
                modifier = textModifier,
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = MaterialTheme.typography.labelMedium.fontSize
            )
        }
    }
}

@Composable
fun ValidatedTextFieldPassword(
    textFieldModifier: Modifier = Modifier.fillMaxWidth(),
    textModifier: Modifier = Modifier,
    label: String,
    value: String,
    error: Boolean,
    errorMessage: String,
    passwordVisible: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    onValueChange: (String) -> Unit,
    onIconClick: ()-> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = textFieldModifier,
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(label) },
            singleLine = true,
            isError = error,
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.ic_visible)
                else painterResource(id = R.drawable.ic_not_visible)

                IconButton(onClick = { onIconClick() }) {
                    Icon(painter = image, contentDescription = null)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
        if (error) {    // error message of email
            Text(
                modifier = textModifier,
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = MaterialTheme.typography.labelMedium.fontSize
            )
        }
    }
}

// common error message
@Composable
fun CommonErrorMessageView(
    commonError: Boolean,
    commonErrorMessage: String,
    onIconClick: () -> Unit
) {
    if (commonError) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(125f),
                text = commonErrorMessage,
                color = MaterialTheme.colorScheme.onErrorContainer,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight(700),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onIconClick() },
                imageVector = Icons.Filled.Close,
                contentDescription = "close error",
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}