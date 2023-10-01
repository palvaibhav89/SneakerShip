package com.example.sneakers.ui.main

import com.example.sneakers.ui.main.model.Product
import com.example.sneakers.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val appDatabase: AppDatabase?
) {
    fun fetchProducts() = flow {
        val list = arrayListOf<Product>()
        for (i in 1..10) {
            list.add(Product.generateProduct())
        }
        emit(list)
    }.flowOn(Dispatchers.IO)
        .catch {
            it.printStackTrace()
        }

    fun fetchCartProducts() =
        appDatabase?.cartDao?.getCartItems()

    suspend fun addProductToCart(product: Product) {
        appDatabase?.cartDao?.insertCartItem(product = product)
    }

    suspend fun removeProductFromCart(product: Product) {
        appDatabase?.cartDao?.deleteCartItem(product = product)
    }
}