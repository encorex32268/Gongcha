package com.lihan.core.database.converter

import androidx.room.TypeConverter

class ListStringConverters {
    @TypeConverter
    fun fromListString(value: List<String>?): String? {
        if (value == null) return null
        return value.joinToString(",")
    }

    @TypeConverter
    fun toListString(value: String?): List<String> {
        if (value.isNullOrBlank()) return emptyList()
        return value.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }
}