package com.example.sneakers.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sneakers.R

sealed class NavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : NavigationScreens("home", R.string.home, Icons.Filled.Home)
    object Cart : NavigationScreens("cart", R.string.cart, Icons.Filled.ShoppingCart)
    object ProductDetail : NavigationScreens("product_detail", R.string.product_detail, Icons.Filled.ShoppingCart)
}