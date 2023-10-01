package com.example.sneakers.ui.productdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.sneakers.R
import com.example.sneakers.views.ChipItem
import com.example.sneakers.views.ClipGroup
import com.example.sneakers.views.ProductImageCarousel
import com.example.sneakers.observers.GlobalObserver
import com.example.sneakers.ui.productdetails.model.ProductDetailPageState
import com.example.sneakers.ui.theme.IconColor
import com.example.sneakers.views.DrawableWrapper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productDetailPageState: ProductDetailPageState
) {
    val product = remember {
        productDetailPageState.product
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .padding(bottom = 20.dp),
                hostState = snackBarHostState)
        },
        containerColor = Color.White
    ) { paddingValues ->
        val verticalScrollState = rememberScrollState()

        ConstraintLayout(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(state = verticalScrollState, enabled = true)
                .fillMaxSize()
        ) {
            val ctx = LocalContext.current
            val (backBtn, carousal, card) = createRefs()

            val productSize = remember {
                product.value?.size?.map {
                    ChipItem(
                        id = it.id,
                        text = it.size.toString()
                    )
                }
            }
            val productColor = remember {
                product.value?.color?.map {
                    ChipItem(
                        id = it.id,
                        containerColor = it.color
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .size(45.dp)
                    .constrainAs(backBtn) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 26.dp)
                    },
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

            ProductImageCarousel(
                modifier = Modifier
                    .constrainAs(carousal) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, 50.dp)
                    }
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp),
                images = listOf(
                    product.value?.media?.imageUrl,
                    product.value?.media?.imageUrl,
                    product.value?.media?.imageUrl
                )
            )

            Surface(
                modifier = Modifier
                    .constrainAs(card) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(carousal.bottom, 24.dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = Color.White,
                elevation = 20.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(28.dp)
                ) {
                    DrawableWrapper(
                        drawableEnd = Icons.Filled.Info,
                        drawableTint = IconColor,
                        drawablePadding = 6.dp
                    ) {
                        Text(
                            modifier = Modifier,
                            text = product.value?.name.toString(),
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.4.sp
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(top = 6.dp),
                        text = product.value?.desc.toString(),
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.4.sp
                    )

                    val sizeState = remember {
                        mutableStateOf(product.value?.selectedSize ?: setOf())
                    }

                    val colorState = remember {
                        mutableStateOf(product.value?.selectedColor ?: setOf())
                    }

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        val (label1, list1, label2, list2) = createRefs()
                        val topBarrier = createEndBarrier(label1, label2)

                        Text(
                            modifier = Modifier
                                .constrainAs(label1) {
                                    start.linkTo(parent.start)
                                    top.linkTo(list1.top)
                                    bottom.linkTo(list1.bottom)
                                }
                                .padding(end = 6.dp),
                            text = stringResource(id = R.string.size),
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.4.sp
                        )

                        ClipGroup(
                            modifier = Modifier
                                .constrainAs(list1) {
                                    top.linkTo(parent.top)
                                    start.linkTo(topBarrier)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                },
                            chipList = productSize ?: listOf(),
                            checkedState = sizeState,
                            textColor = IconColor,
                            strokeWidth = 1.dp,
                            strokeColor = IconColor,
                            containerColor = Color.Transparent.value.toLong(),
                            cornerRadius = 10.dp,
                            horizontalPadding = 10.dp
                        )

                        Text(
                            modifier = Modifier
                                .constrainAs(label2) {
                                    start.linkTo(parent.start)
                                    top.linkTo(list2.top)
                                    bottom.linkTo(list2.bottom)
                                }
                                .padding(end = 6.dp),
                            text = stringResource(id = R.string.colour),
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.4.sp
                        )

                        ClipGroup(
                            modifier = Modifier
                                .constrainAs(list2) {
                                    start.linkTo(topBarrier)
                                    top.linkTo(list1.bottom)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                },
                            chipList = productColor ?: listOf(),
                            checkedState = colorState,
                            cornerRadius = 16.dp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(end = 6.dp),
                            text = stringResource(id = R.string.price_colon),
                            color = Color.Gray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.4.sp
                        )

                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 6.dp),
                            text = stringResource(id = R.string.price, product.value?.retailPrice ?: 0),
                            color = IconColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.4.sp
                        )

                        Button(
                            onClick = {
                                val cartProduct = product.value?.apply {
                                    selectedSize = sizeState.value
                                    selectedColor = colorState.value
                                } ?: return@Button
                                GlobalObserver.mainObserver.addToCart(cartProduct)
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(message = ctx.getString(R.string.product_added_to_cart))
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = IconColor)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(end = 6.dp),
                                text = stringResource(id = R.string.add_to_cart),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.4.sp
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
fun ProductDetailPreview() {
    ProductDetailScreen(
        ProductDetailPageState(
            remember {
                mutableStateOf(null)
            }
        )
    )
}

