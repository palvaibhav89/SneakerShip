package com.example.sneakers.room

import androidx.room.TypeConverter
import com.example.sneakers.ui.main.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartTypeConverters {

    @TypeConverter
    fun fromStringToSizeList(value: String?): List<Product.Size>? {
        val listType = object : TypeToken<List<Product.Size>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringToColorList(value: String?): List<Product.Color>? {
        val listType = object : TypeToken<List<Product.Color>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListToString(list: List<*>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToSet(value: String?): Set<String>? {
        val listType = object : TypeToken<Set<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromSetToString(set: Set<String>): String? {
        return Gson().toJson(set)
    }
}