package com.example.sneakers.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakers.ui.cart.model.CartPageState
import com.example.sneakers.ui.home.model.HomePageState
import com.example.sneakers.observers.MainObserver
import com.example.sneakers.ui.productdetails.model.ProductDetailPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    init {
        fetchProducts()

        fetchCartProducts()
    }

    val bottomNavScreens = listOf(
        NavigationScreens.Home,
        NavigationScreens.Cart
    )

    val homePageState = HomePageState()
    val cartPageState = CartPageState()
    val productDetailPageState = ProductDetailPageState()

    private fun fetchProducts() {
        viewModelScope.launch {
            mainRepository.fetchProducts().collect {
                homePageState.productList.value = it
            }
        }
    }

    private fun fetchCartProducts() {
        viewModelScope.launch {
            mainRepository.fetchCartProducts()?.collectLatest {
                cartPageState.productList.value = it
            }
        }
    }

    fun handleCartEvents(mainEvent: MainObserver.MainEvent) {
        viewModelScope.launch {
            if (mainEvent is MainObserver.MainEvent.AddToCart) {
                mainRepository.addProductToCart(mainEvent.product)
            } else if (mainEvent is MainObserver.MainEvent.RemoveFromCart) {
                mainRepository.removeProductFromCart(mainEvent.product)
            }
        }
    }
}