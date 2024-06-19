package com.learn.sevasahyog.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

@Preview
@Composable
private fun DataViewInCardPrev() {
    DataViewInCard(image = Icons.Filled.Face, info = "Chirag Nikam", infoDesc = "Name")
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