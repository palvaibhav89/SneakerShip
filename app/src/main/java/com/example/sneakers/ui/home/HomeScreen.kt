package com.example.sneakers.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.sneakers.R
import com.example.sneakers.ui.home.model.HomePageState
import com.example.sneakers.ui.main.model.Product
import com.example.sneakers.observers.GlobalObserver
import com.example.sneakers.ui.theme.IconColor
import com.example.sneakers.ui.theme.SneakerTypography
import com.example.sneakers.views.DrawableWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomePageState
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.White
    ) { paddingValues ->
        paddingValues.toString()
        ConstraintLayout(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            val (heading, search, sortedBy, sortBy, sneakerListView) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(heading) {
                        start.linkTo(parent.start)
                        top.linkTo(search.top)
                        bottom.linkTo(search.bottom)
                    },
                text = stringResource(id = R.string.sneaker_ship).uppercase(),
                color = IconColor,
                style = SneakerTypography.labelMedium,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                modifier = Modifier
                    .constrainAs(search) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = IconColor
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(sortedBy) {
                        start.linkTo(heading.start)
                        top.linkTo(heading.bottom, 24.dp)
                    },
                text = stringResource(id = R.string.popular),
                color = Color.Black,
                style = SneakerTypography.titleSmall
            )

            DrawableWrapper(
                modifier = Modifier
                    .constrainAs(sortBy) {
                        end.linkTo(search.end)
                        top.linkTo(sortedBy.top)
                        bottom.linkTo(sortedBy.bottom)
                    },
                drawableEnd = Icons.Default.KeyboardArrowDown,
                drawableTint = Color.Gray
            ) {
                Text(
                    text = stringResource(id = R.string.sort_by),
                    color = Color.Gray,
                    style = SneakerTypography.labelSmall
                )
            }

            val listState = rememberLazyGridState()

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .constrainAs(sneakerListView) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(sortedBy.bottom, 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                state = listState,
                contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.productList.value) {
                    SneakerItem(
                        product = it,
                        addToCartClick = {
                            GlobalObserver.mainObserver.addToCart(it)
                        },
                        productClickListener = {
                            GlobalObserver.mainObserver.openProductDetailFor(it)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen(HomePageState().apply {
        productList.value = listOf(
            Product.generateProduct(),
            Product.generateProduct()
        )
    })
}

