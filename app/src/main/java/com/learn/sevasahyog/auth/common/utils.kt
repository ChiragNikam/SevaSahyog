package com.learn.sevasahyog.auth.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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