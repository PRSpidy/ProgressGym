package com.example.progressgym

import android.content.Context
import android.content.SharedPreferences

class UserPreferences() {

    private val sharedPreferences: SharedPreferences by lazy {
        MyApp.context.getSharedPreferences(MyApp.context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    companion object {
        const val USERNAME = "name"
    }

    fun saveUserName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USERNAME, name)
        editor.apply()
    }

    fun getUserName(): String? {
        val userJson = sharedPreferences.getString(USERNAME, null)
        return if (userJson != null) {
            userJson
        } else {
            null
        }
    }
}