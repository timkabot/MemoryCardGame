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

}
