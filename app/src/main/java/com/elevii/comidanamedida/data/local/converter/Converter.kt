package com.elevii.comidanamedida.data.local.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converter {
    @TypeConverter
    fun fromLocalDate(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }
}
