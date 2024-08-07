package ru.vsibi.btc_mathematic.network.util

import ru.vsibi.btc_mathematic.util.parseLocalDate
import ru.vsibi.btc_mathematic.util.parseLocalDateTime
import ru.vsibi.btc_mathematic.util.parseZonedDateTime
import java.time.*
import java.time.format.DateTimeFormatter

object NetworkMapperUtil {

    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun parseLocalDate(date: String): LocalDate = dateFormat.parseLocalDate(date)

    fun formatLocalDate(localDate: LocalDate): String = dateFormat.format(localDate)

    private val localDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    fun parseLocalDateTime(date: String): LocalDateTime = localDateTimeFormat.parseLocalDateTime(date)

    fun formatLocalDateTime(localDateTime: LocalDateTime): String = localDateTimeFormat.format(localDateTime)


    private val zonedDateTimeFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun parseZonedDateTime(date: String): ZonedDateTime = zonedDateTimeFormat.parseZonedDateTime(date)

    fun formatZonedDateTime(zonedDateTime: ZonedDateTime): String = zonedDateTimeFormat.format(zonedDateTime)

}