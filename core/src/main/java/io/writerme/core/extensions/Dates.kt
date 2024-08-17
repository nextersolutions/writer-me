package io.writerme.core.extensions

import io.writerme.core.common.FormatUtils.Patterns.dateFormat
import io.writerme.core.common.FormatUtils.Patterns.monthDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toMonthDay(): String {
    val formatter = DateTimeFormatter.ofPattern(monthDate)
    return this.format(formatter)
}

fun LocalDate.format(): String {
    val formatter = DateTimeFormatter.ofPattern(dateFormat)
    return this.format(formatter)
}
