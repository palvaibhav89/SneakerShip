package com.example.sneakers.ui.cart

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.sneakers.R
import com.example.sneakers.ui.main.model.Product
import com.example.sneakers.ui.theme.IconColor
import com.example.sneakers.views.ProductImage

@Composable
fun CartProductView(
    cartProduct: Product,
    removeProduct: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(10.dp)
    ) {
        val (remove, details) = createRefs()

        Card(
            modifier = Modifier
                .constrainAs(details) {

                },
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (productImage, title, price, spacer) = createRefs()
                createVerticalChain(title, spacer, price, chainStyle = ChainStyle.Packed)

                ProductImage(
                    modifier = Modifier
                        .constrainAs(productImage) {
                            start.linkTo(parent.start, 16.dp)
                            top.linkTo(parent.top, 16.dp)
                            bottom.linkTo(parent.bottom, 16.dp)
                            width = Dimension.percent(.4f)
                        },
                    imageUrl = cartProduct.media.imageUrl
                )

                Text(
                    modifier = Modifier
                        .constrainAs(title) {
                            start.linkTo(productImage.end, 12.dp)
                            end.linkTo(parent.end, 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    text = cartProduct.name,
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.4.sp
                )

                Spacer(
                    modifier = Modifier
                        .constrainAs(spacer) {
                            start.linkTo(title.start)
                            end.linkTo(title.end)
                            width = Dimension.fillToConstraints
                        }
                        .fillMaxWidth()
                        .height(12.dp)
                )

                Text(
                    modifier = Modifier
                        .constrainAs(price) {
                            start.linkTo(title.start)
                            end.linkTo(title.end)
                            width = Dimension.fillToConstraints
                        },
                    text = stringResource(id = R.string.price, cartProduct.retailPrice),
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.4.sp
                )
            }
        }

        IconButton(
            modifier = Modifier
                .constrainAs(remove) {
                    start.linkTo(details.end)
                    end.linkTo(details.end)
                    top.linkTo(details.top)
                    bottom.linkTo(details.top)
                },
            onClick = {
                removeProduct()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.AddCircle,
                modifier = Modifier
                    .rotate(45f),
                contentDescription = "",
                tint = IconColor
            )
        }
    }
}

@Preview
@Composable
fun CartProductPreview() {
    CartProductView(
        Product.generateProduct()
    ) {

    }
}