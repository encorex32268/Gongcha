package com.lihan.core.database.converter

import androidx.room.TypeConverter
import com.lihan.core.domain.model.BusinessHour
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
internal data class BusinessHourDto(
    val day: Int,
    val open: String,
    val close: String
)

class Converters {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @TypeConverter
    fun fromBusinessHourList(value: List<BusinessHour>?): String? {
        if (value == null) return null
        val dtoList = value.map { BusinessHourDto(it.day, it.open, it.close) }
        return json.encodeToString(dtoList)
    }

    @TypeConverter
    fun toBusinessHourList(value: String?): List<BusinessHour> {
        if (value.isNullOrEmpty()) return emptyList()
        return try {
            json.decodeFromString<List<BusinessHourDto>>(value).map {
                BusinessHour(
                    day = it.day,
                    open = it.open,
                    close = it.close
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
