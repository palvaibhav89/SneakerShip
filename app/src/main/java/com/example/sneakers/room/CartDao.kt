package com.example.sneakers.room

import androidx.room.*
import com.example.sneakers.ui.main.model.Product
import com.example.sneakers.room.AppDatabase.Companion.TABLE_CART
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM $TABLE_CART")
    fun getCartItems(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(product: Product)

    @Delete
    suspend fun deleteCartItem(product: Product)
}