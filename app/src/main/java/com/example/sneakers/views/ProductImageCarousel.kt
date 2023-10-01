package com.example.sneakers.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.sneakers.R
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.yield

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImageCarousel(
    modifier: Modifier = Modifier,
    images: List<Int?>
) {
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = modifier
    ) {
        val (left, right, pager, indicators) = createRefs()

        val pagerState = rememberPagerState {
            images.size
        }

        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(2600)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                )
            }
        }

        IconButton(
            modifier = Modifier
                .constrainAs(left) {
                    start.linkTo(parent.start)
                    top.linkTo(pager.top)
                    bottom.linkTo(pager.bottom)
                }
                .size(50.dp),
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage - 1) % (pagerState.pageCount)
                    )
                }
            }
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(id = R.string.left),
                tint = Color.LightGray
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .constrainAs(pager) {
                    start.linkTo(left.end)
                    end.linkTo(right.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(indicators.top)
                    width = Dimension.fillToConstraints
                }
        ) {
            ProductImage(imageUrl = images[it])
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .constrainAs(indicators) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(pager.bottom, 24.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.preferredWrapContent
                },
            pagerState = pagerState,
            pageCount = images.size,
            indicatorWidth = 48.dp,
            indicatorHeight = 4.dp,
            spacing = 4.dp,
            indicatorShape = RoundedCornerShape(16.dp)
        )

        IconButton(
            modifier = Modifier
                .constrainAs(right) {
                    end.linkTo(parent.end)
                    top.linkTo(pager.top)
                    bottom.linkTo(pager.bottom)
                }
                .size(50.dp),
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                    )
                }
            }
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.right),
                tint = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
fun ProductImageCarouselPreview() {
    ProductImageCarousel(
        modifier = Modifier
        .fillMaxWidth(),
        images = listOf(
            R.drawable.sneaker,
            R.drawable.sneaker
        )
    )
}