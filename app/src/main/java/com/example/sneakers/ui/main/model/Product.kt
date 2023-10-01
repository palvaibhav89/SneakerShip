package com.example.sneakers.ui.main.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sneakers.R
import com.example.sneakers.room.AppDatabase.Companion.TABLE_CART
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = TABLE_CART)
data class Product(
    @PrimaryKey
    val id: String,
    val retailPrice: Int,
    val name: String,
    val desc: String,
    @Embedded
    val media: Media,
    val size: List<Size>,
    val color: List<Color>,
    var selectedSize: Set<String> = mutableSetOf(),
    var selectedColor: Set<String> = mutableSetOf(),
): Parcelable {

    @Parcelize
    data class Media(
        val imageUrl: Int
    ): Parcelable

    @Parcelize
    data class Size(
        val id: String,
        val size: Int
    ): Parcelable

    @Parcelize
    data class Color(
        val id: String,
        val color: Long
    ): Parcelable

    companion object {
        fun generateProduct(): Product {
            val selectedSizeId = UUID.randomUUID().toString()
            val selectedColorId = UUID.randomUUID().toString()
            return Product(
                id = UUID.randomUUID().toString(),
                retailPrice = (100..990).random(),
                name = "Nike Air " + (1..100).random(),
                desc = "Sed ut perspiciatis omnis iste",
                media = Media(
                    imageUrl = R.drawable.sneaker
                ),
                size = listOf(
                    Size(selectedSizeId, (1..10).random()),
                    Size(UUID.randomUUID().toString(), (1..10).random()),
                    Size(UUID.randomUUID().toString(), (1..10).random())
                ),
                color = listOf(
                    Color(selectedColorId, 0xFFfd9579),
                    Color(UUID.randomUUID().toString(), 0xFF0000FF),
                    Color(UUID.randomUUID().toString(), 0xFF00FFFF)
                ),
                selectedSize = setOf(selectedSizeId),
                selectedColor = setOf(selectedColorId)
            )
        }
    }
}