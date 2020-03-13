package com.app.memorycardgame.model.data.storage

import android.content.Context
import com.app.memorycardgame.entity.Player
import com.app.memorycardgame.utils.APP_PREFERENCES
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject


class Prefs @Inject constructor(private val context: Context){
    fun saveArrayList( list: List<String>, key: String?) {
        val prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String?): ArrayList<String?>? {
        val prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type)
    }
}