package com.example.sneakers.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sneakers.R
import com.example.sneakers.ui.main.model.Product
import com.example.sneakers.ui.theme.IconColor
import com.example.sneakers.views.ProductImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SneakerItem(
    product: Product,
    productClickListener: (Product) -> Unit,
    addToCartClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = {
            productClickListener(product)
        },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(8.dp)
        ) {
            val (addToCart, imageBg, name, price) = createRefs()

            Icon(
                modifier = Modifier
                    .clickable(onClick = addToCartClick)
                    .constrainAs(addToCart) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = stringResource(id = R.string.add_to_cart),
                tint = IconColor
            )

            ProductImage(
                modifier = Modifier
                    .constrainAs(imageBg) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, 8.dp)
                    },
                imageUrl = product.media.imageUrl
            )

            Text(
                modifier = Modifier
                    .constrainAs(name) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(imageBg.bottom, 12.dp)
                    },
                text = product.name,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.4.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .constrainAs(price) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(name.bottom, 12.dp)
                    },
                text = stringResource(id = R.string.price, product.retailPrice),
                color = IconColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.4.sp
            )
        }
    }
}

@Preview
@Composable
fun SneakerItemPreview() {
    SneakerItem(
        Product.generateProduct(), {}
    ) {

    }
}

