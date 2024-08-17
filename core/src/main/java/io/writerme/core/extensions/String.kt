package io.writerme.core.extensions

import android.annotation.SuppressLint
import androidx.compose.ui.text.input.TextFieldValue
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.Patterns
import io.writerme.core.common.FormatUtils.Patterns.birthDateFormat
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.common.FormatUtils.VALUE_4
import io.writerme.core.common.FormatUtils.VALUE_6
import io.writerme.core.common.FormatUtils.ZERO
import io.writerme.core.common.FormatUtils.plus
import io.writerme.core.common.FormatUtils.star
import java.text.SimpleDateFormat
import java.util.Date

fun String.isNumeric(): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return this.matches(regex)
}

fun String.toOptFormat(): String {
    val trimmedOtp = this.trim()
        .replace(Patterns.numericFormatRegex.toRegex(), EMPTY)
    return if (trimmedOtp.length > VALUE_6) {
        trimmedOtp
            .substring(ZERO, VALUE_6)
    } else {
        trimmedOtp
    }
}

fun String.toFullPhone(): String {
    return if (this.startsWith(plus).not()) {
        "$plus$this"
    } else {
        this
    }
}

fun String.digitsOnly(): String {
    return this.trim()
        .replace(Patterns.numericFormatRegex.toRegex(), EMPTY)
}

fun TextFieldValue.digitsOnly(): TextFieldValue {
    val text = this.text
    return TextFieldValue(text.digitsOnly())
}

fun String.onlyPhoneNumber(): String {
    val value = this.trim()
    if (value.startsWith("$ZERO")) {
        return value.substring(VALUE_1, value.length)
    }
    return value
}

fun String.isLengthValid(length: Int): Boolean {
    return this.length >= length
}

fun String.removeLast(): String {
    return if (this.isNotEmpty()) {
        if (this.length == VALUE_1) {
            EMPTY
        } else {
            this.substring(ZERO, this.length - VALUE_1)
        }
    } else {
        EMPTY
    }
}

fun Int.toTimerText(): String {
    return "00:$this"
}

@SuppressLint("SimpleDateFormat")
fun Date?.toBirthDate(): String? {
    return if (this != null) {
        val dateFormatter = SimpleDateFormat(birthDateFormat)
        return dateFormatter.format(this)
    } else {
        null
    }
}

fun String.hideMainCardNumber(): String {
    val cleaned = this.digitsOnly()

    if (cleaned.length < VALUE_4) return cleaned
    val last = cleaned.takeLast(VALUE_4)
    return "$star$last"
}
