package io.github.steelahhh.rent.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

object FlatTypeConverter {
    @TypeConverter
    fun stringToFlatList(json: String): List<Flat> {
        val gson = Gson()
        val type = object : TypeToken<List<Flat>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun flatListToString(list: List<Flat>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Flat>>() {}.type
        return gson.toJson(list, type)
    }
}