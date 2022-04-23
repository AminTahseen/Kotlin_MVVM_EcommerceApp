package com.example.kotlinmvvm_ecommerce.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.kotlinmvvm_ecommerce.models.user.UserItem
import com.google.gson.Gson


class PrefManager(context: Context) {
    private val sharedPrefFile = "ecommerce_SharedPref"
    private val userKey = "loggedInUser_key"
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

     fun storeUserInPref(userItem: UserItem) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(userItem)
        editor.putString(userKey, json)
        editor.commit()
    }

    fun getUserFromPref(): UserItem {
        val gson = Gson()
        val json: String? = sharedPreferences.getString(userKey, "")
        return gson.fromJson(json, UserItem::class.java)
    }

}