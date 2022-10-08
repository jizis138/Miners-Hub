package ru.vsibi.momento.data.db.converter

import androidx.room.TypeConverter
import ru.vsibi.momento.util.parseLocalDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    @TypeConverter
    fun fromLocalDate(localDateTime: LocalDateTime?): String? = localDateTime?.let(dateFormatter::format)

    @TypeConverter
    fun toLocalDate(string: String?): LocalDateTime? = string?.let(dateFormatter::parseLocalDateTime)
}