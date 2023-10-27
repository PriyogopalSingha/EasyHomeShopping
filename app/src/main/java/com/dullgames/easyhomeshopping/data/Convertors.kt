package com.dullgames.easyhomeshopping.data

import androidx.room.TypeConverter
import com.dullgames.easyhomeshopping.data.model.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Convertors {
    @TypeConverter
    fun fromItemList(value: List<Item>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Item>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toItemList(value: String): List<Item> {
        val gson = Gson()
        val type = object : TypeToken<List<Item>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromItem(value: Item): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toItem(value: String): Item{
        val gson = Gson()
        val objectType = object : TypeToken<Item>() {}.type
        return gson.fromJson(value, objectType)
    }
}