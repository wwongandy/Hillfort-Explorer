package org.wit.hillfortexplorer.models.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class HillfortConverters {

    val hillfortImagesType = object : TypeToken<ArrayList<String>>(){}.type

    // For converting dateVisited variables
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateVisitedToTimestamp(dateVisited: Date?): Long? {
        return dateVisited?.time?.toLong()
    }

    // For converting the listing of images
    @TypeConverter
    fun stringToList(value: String?): List<String>? {
        if (value == null) {
            return ArrayList()
        }

        return Gson().fromJson(value, hillfortImagesType)
    }

    @TypeConverter
    fun imagesToString(images: List<String>): String? {
        return Gson().toJson(images)
    }
}