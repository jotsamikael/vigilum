package com.example.vigilum.utils

import android.content.Context
import android.content.SharedPreferences


fun checkFirstLaunch(context: Context): Boolean {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)
    if (isFirstLaunch) {
        // Update the preference to mark the app as launched
        sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
    }
    return isFirstLaunch
}
