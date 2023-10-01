package com.example.sneakers.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sneakers.ui.main.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(CartTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "sneaker_ship_db"
        const val TABLE_CART = "cart"
    }

    abstract val cartDao: CartDao
}