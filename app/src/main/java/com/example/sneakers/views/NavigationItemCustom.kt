package com.example.sneakers.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sneakers.ui.theme.IconColor

@Composable
fun NavigationItemCustom(
    modifier: Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
) {
    IconButton(
        modifier = modifier
            .clip(CircleShape)
            .background(
                if (selected) {
                    IconColor
                } else {
                    Color.Transparent
                }
            )
            .padding(10.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(),
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (selected) {
                Color.White
            } else {
                IconColor
            }
        )
    }
}

@Preview
@Composable
fun NavigationItemPreview() {
    NavigationItemCustom(Modifier,true, {}, Icons.Filled.Home, "")
}