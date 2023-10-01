package com.example.sneakers.ui.productdetails.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.sneakers.ui.main.model.Product

data class ProductDetailPageState(
    var product: MutableState<Product?> = mutableStateOf(null)
)
