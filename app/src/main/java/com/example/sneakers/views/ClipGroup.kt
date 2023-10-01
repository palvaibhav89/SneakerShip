package com.example.sneakers.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClipGroup(
    modifier: Modifier = Modifier,
    chipList: List<ChipItem>,
    checkedState: MutableState<Set<String>>,
    textColor: Color = Color.Black,
    selectedTextColor: Color = Color.White,
    selectedDrawable: ImageVector = Icons.Filled.Done,
    strokeColor: Color = Color.Transparent,
    strokeWidth: Dp = 0.dp,
    containerColor: Long? = null,
    selectedContainerColor: Long = 0xFFfd9579,
    cornerRadius: Dp = 4.dp,
    horizontalPadding: Dp = 20.dp,
    chipPadding: Dp = 8.dp,
    singleSelect: Boolean = true
) {
    val rowState = rememberLazyListState()

    LazyRow(
        modifier = modifier,
        state = rowState,
        horizontalArrangement = Arrangement.spacedBy(chipPadding)
    ) {
        items(chipList) { item ->
            val backgroundColor = containerColor ?: item.containerColor ?: 0xFF888888
            Chip(
                modifier = Modifier
                    .width(intrinsicSize = IntrinsicSize.Max),
                onClick = {
                    val list = checkedState.value.toMutableList()
                    if (singleSelect) {
                        list.clear()
                        list.add(item.id)
                    } else if (list.contains(item.id)) {
                        list.remove(item.id)
                    } else {
                        list.add(item.id)
                    }
                    checkedState.value = list.toSet()
                },
                shape = RoundedCornerShape(cornerRadius),
                border = BorderStroke(strokeWidth, strokeColor),
                colors = if (checkedState.value.contains(item.id) && item.text.isNotEmpty()) {
                    ChipDefaults.chipColors(backgroundColor = Color(selectedContainerColor))
                } else {
                    ChipDefaults.chipColors(backgroundColor = Color(backgroundColor))
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (item.text.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(start = horizontalPadding, end = horizontalPadding)
                                .align(Alignment.Center),
                            text = item.text,
                            color = if (checkedState.value.contains(item.id)) {
                                selectedTextColor
                            } else {
                                textColor
                            },
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.4.sp
                        )
                    } else if (checkedState.value.contains(item.id)) {
                        Icon(
                            imageVector = selectedDrawable,
                            contentDescription = "",
                            tint = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ClipGroupPreview() {
    ClipGroup(
        chipList = listOf(
            ChipItem(
                id = UUID.randomUUID().toString(),
                text = (1..9).random().toString()
            ),
            ChipItem(
                id = UUID.randomUUID().toString(),
                containerColor = 0xFF0000FF
            )
        ),
        checkedState = remember {
            mutableStateOf(setOf())
        }
    )
}

data class ChipItem(
    val id: String,
    val text: String = "",
    val containerColor: Long? = null
)