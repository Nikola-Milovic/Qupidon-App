package com.nikolam.common.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nikolam.common.db.models.MessageDataModel

object Converters {
    @TypeConverter
    fun fromArrayList(list: ArrayList<MessageDataModel?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}