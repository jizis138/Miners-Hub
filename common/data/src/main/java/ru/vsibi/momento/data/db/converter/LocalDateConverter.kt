package ru.vsibi.momento.data.db.converter

import androidx.room.TypeConverter
import ru.vsibi.momento.util.parseLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): String? = localDate?.let(dateFormatter::format)

    @TypeConverter
    fun toLocalDate(string: String?): LocalDate? = string?.let(dateFormatter::parseLocalDate)
}