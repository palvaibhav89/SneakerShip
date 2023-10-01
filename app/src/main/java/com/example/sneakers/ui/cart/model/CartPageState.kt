package com.example.sneakers.ui.cart.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.sneakers.ui.main.model.Product

data class CartPageState(
    val productList: MutableState<List<Product>> = mutableStateOf(arrayListOf())
)