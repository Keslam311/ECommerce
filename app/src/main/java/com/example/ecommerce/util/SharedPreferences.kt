package com.example.ecommerce.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFS_NAME = "app_preferences"
    private const val FAVORITE_KEY_PREFIX = "favorite_"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isFavorite(context: Context, productId: String): Boolean {
        return getPreferences(context).getBoolean(FAVORITE_KEY_PREFIX + productId, false)
    }

    fun setFavorite(context: Context, productId: String, isFavorite: Boolean) {
        getPreferences(context).edit().putBoolean(FAVORITE_KEY_PREFIX + productId, isFavorite).apply()
    }
}

