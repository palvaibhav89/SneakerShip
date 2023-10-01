package com.example.sneakers.utils

import android.content.Intent
import android.os.Build

object Utils {

}

@Suppress("DEPRECATION")
inline fun <reified T> Intent.parcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        getParcelableExtra(key)
    }
}