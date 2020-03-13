package com.app.memorycardgame.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Utils {
    fun getDrawableByName(name: String, context: Context): Drawable {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier(name, "drawable", context.packageName)
        return resources.getDrawable(resourceId)
    }

    fun saveArrayList(context: Context, list: List<String>, key: String?) {
        val prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(context: Context, key: String?): ArrayList<String?>? {
        val prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type)
    }

}
