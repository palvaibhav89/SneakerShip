package com.example.sneakers.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sneakers.R
import com.example.sneakers.ui.cart.model.CartPageState
import com.example.sneakers.ui.main.model.Product
import com.example.sneakers.observers.GlobalObserver
import com.example.sneakers.ui.theme.IconColor
import com.example.sneakers.ui.theme.SneakerTypography
import com.example.sneakers.views.DrawableWrapper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartPageState: CartPageState
) {

    val cartProducts by remember {
        cartPageState.productList
    }

    val subTotalValue = cartProducts.sumOf {
        it.retailPrice
    }
    val taxes = if (cartProducts.isNotEmpty()) {
        ((.05 * subTotalValue) + 10).toInt()
    } else {
        0
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp)
            ) {
                IconButton(
                    modifier = Modifier
                       .size(45.dp),
                    onClick = {
                        GlobalObserver.mainObserver.navUp()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                           .fillMaxSize(),
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(id = R.string.back),
                        tint = IconColor
                    )
                }

                Text(
                    modifier = Modifier
                       .align(Alignment.Center),
                    text = stringResource(id = R.string.cart),
                    color = IconColor,
                    style = SneakerTypography.titleLarge
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding() + 12.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cartProducts) {
                CartProductView(cartProduct = it) {
                    GlobalObserver.mainObserver.removeFromCart(product = it)
                }
            }

            if (cartProducts.isEmpty()) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cart_empty),
                            contentDescription = stringResource(id = R.string.cart_empty)
                        )

                        Text(
                            text = stringResource(id = R.string.cart_empty),
                            color = Color.Gray,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.4.sp
                        )
                    }
                }
            } else {
                item {
                    ConstraintLayout(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {
                        val (orderTitle, subTotal, taxesCharges, total, totalPrice, checkoutBtn) = createRefs()

                        DrawableWrapper(
                            modifier = Modifier
                                .constrainAs(orderTitle) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                },
                            drawableEnd = Icons.Filled.Info,
                            drawableTint = IconColor,
                            drawablePadding = 6.dp
                        ) {
                            Text(
                                text = stringResource(id = R.string.order_details),
                                color = Color.Black,
                                style = SneakerTypography.titleMedium
                            )
                        }

                        Text(
                            modifier = Modifier
                                .constrainAs(subTotal) {
                                    start.linkTo(orderTitle.start)
                                    top.linkTo(orderTitle.bottom, 12.dp)
                                },
                            text = stringResource(id = R.string.subtotal_dollar, subTotalValue),
                            color = Color.Gray,
                            style = SneakerTypography.labelMedium
                        )

                        Text(
                            modifier = Modifier
                                .constrainAs(taxesCharges) {
                                    start.linkTo(orderTitle.start)
                                    top.linkTo(subTotal.bottom, 4.dp)
                                },
                            text = stringResource(id = R.string.taxes_charges, taxes),
                            color = Color.Gray,
                            style = SneakerTypography.labelMedium
                        )

                        Text(
                            modifier = Modifier
                                .constrainAs(total) {
                                    start.linkTo(orderTitle.start)
                                    top.linkTo(checkoutBtn.top)
                                    bottom.linkTo(checkoutBtn.bottom)
                                },
                            text = stringResource(id = R.string.total),
                            color = Color.Gray,
                            style = SneakerTypography.labelLarge
                        )

                        Text(
                            modifier = Modifier.
                            constrainAs(totalPrice) {
                                start.linkTo(total.end, 4.dp)
                                top.linkTo(total.top)
                                bottom.linkTo(total.bottom)
                            },
                            text = stringResource(id = R.string.price, (subTotalValue + taxes)),
                            color = IconColor,
                            style = SneakerTypography.titleSmall
                        )

                        Button(
                            modifier = Modifier
                                .constrainAs(checkoutBtn) {
                                    end.linkTo(parent.end)
                                    top.linkTo(taxesCharges.bottom, 16.dp)
                                },
                            onClick = {
                                GlobalObserver.mainObserver.proceedToCheckout()
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = IconColor)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(end = 6.dp),
                                text = stringResource(id = R.string.dollar_checkout),
                                color = Color.White,
                                style = SneakerTypography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun CartComposablePreview() {
    CartScreen(CartPageState().apply {
        productList.value = listOf(
            Product.generateProduct(),
            Product.generateProduct()
        )
    })
}