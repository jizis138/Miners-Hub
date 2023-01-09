package ru.vsibi.btc_mathematic.data.db.converter

import androidx.room.TypeConverter
import ru.vsibi.btc_mathematic.util.parseLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): String? = localDate?.let(dateFormatter::format)

    @TypeConverter
    fun toLocalDate(string: String?): LocalDate? = string?.let(dateFormatter::parseLocalDate)
}