package ru.vsibi.momento.data.db.converter

import androidx.room.TypeConverter
import ru.vsibi.momento.util.parseZonedDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeConverter {
    private val dateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun fromLocalDate(zonedDateTime: ZonedDateTime?): String? = zonedDateTime?.let(dateFormatter::format)

    @TypeConverter
    fun toLocalDate(string: String?): ZonedDateTime? = string?.let(dateFormatter::parseZonedDateTime)
}