package com.example.sneakers.views

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DrawableWrapper(
    modifier: Modifier = Modifier,
    drawableTop: ImageVector? = null,
    drawableStart: ImageVector? = null,
    drawableBottom: ImageVector? = null,
    drawableEnd: ImageVector? = null,
    drawableTint: Color = Color.Black,
    drawablePadding: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    ConstraintLayout(modifier) {
        val (refImgStart, refImgTop, refImgBottom, refImgEnd, refContent) = createRefs()
        Box(Modifier.constrainAs(refContent) {
            if (drawableTop == null) {
                top.linkTo(parent.top)
            } else {
                top.linkTo(refImgTop.bottom, drawablePadding)
            }
            if (drawableBottom == null) {
                bottom.linkTo(parent.bottom)
            } else {
                bottom.linkTo(refImgBottom.top, drawablePadding)
            }
            if (drawableStart == null) {
                start.linkTo(parent.start)
            } else {
                start.linkTo(refImgStart.end, drawablePadding)
            }
            if (drawableEnd == null) {
                end.linkTo(parent.end)
            } else {
                end.linkTo(refImgEnd.start, drawablePadding)
            }
        }) {
            content()
        }
        drawableTop?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.constrainAs(refImgTop) {
                    top.linkTo(parent.top)
                    start.linkTo(refContent.start)
                    end.linkTo(refContent.end)
                },
                tint = drawableTint
            )
        }
        drawableStart?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.constrainAs(refImgStart) {
                    top.linkTo(refContent.top)
                    bottom.linkTo(refContent.bottom)
                    start.linkTo(parent.start)
                },
                tint = drawableTint
            )
        }
        drawableBottom?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.constrainAs(refImgBottom) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(refContent.start)
                    end.linkTo(refContent.end)
                },
                tint = drawableTint
            )
        }
        drawableEnd?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.constrainAs(refImgEnd) {
                    top.linkTo(refContent.top)
                    bottom.linkTo(refContent.bottom)
                    end.linkTo(parent.end)
                },
                tint = drawableTint
            )
        }
    }
}