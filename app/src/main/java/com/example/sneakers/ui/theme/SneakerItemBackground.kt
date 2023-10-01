package com.example.sneakers.ui.theme

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SneakerItemBackground(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .aspectRatio(ratio = 1f),
        shape = CircleShape,
        color = OrangeLight
    ) {
        Surface(
            modifier = Modifier
                .padding(22.dp)
                .aspectRatio(ratio = 1f),
            shape = CircleShape,
            color = OrangeMid
        ) {

        }
    }
}

@Preview
@Composable
fun SneakerItemBgPreview() {
    SneakerItemBackground()
}