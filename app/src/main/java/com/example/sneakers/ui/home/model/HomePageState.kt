package com.example.sneakers.ui.home.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.sneakers.ui.main.model.Product

data class HomePageState(
    val productList: MutableState<List<Product>> = mutableStateOf(arrayListOf())
)