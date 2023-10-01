package com.example.sneakers.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sneakers.R
import com.example.sneakers.ui.theme.SneakerItemBackground

@Composable
fun ProductImage(
    modifier: Modifier = Modifier,
    imageUrl: Int?
) {
    Box(
        modifier = modifier
            .aspectRatio(ratio = 1f),
        contentAlignment = Alignment.Center
    ) {
        SneakerItemBackground()

        Image(
            painter = painterResource(id = imageUrl ?: R.drawable.sneaker),
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun ProductImagePreview() {
    ProductImage(imageUrl = R.drawable.sneaker)
}