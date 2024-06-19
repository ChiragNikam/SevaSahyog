package com.learn.sevasahyog.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
    Row (modifier = modifier){
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
    DataViewInCard(image = Icons.Filled.Face, info = "Chirag Nikam", infoDesc = "Name", )
}