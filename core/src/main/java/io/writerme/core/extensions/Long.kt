package io.writerme.core.extensions

import io.writerme.core.common.FormatUtils.EMPTY

fun Long.toTime(): String {
    var result = EMPTY
    var quotient = this
    var remainder: Long

    val seconds = 60
    val minutes = seconds
    val hours = 24
    val days = 365

    var i = 1

    do {
        val divider = when (i) {
            1 -> seconds
            2 -> minutes
            3 -> hours
            else -> days
        }

        remainder = quotient % divider
        quotient /= divider

        result = if (i > 1) {
            "$remainder:$result"
        } else {
            val rem = if (remainder < 10) {
                "0$remainder"
            } else remainder.toString()

            if (quotient > 0) {
                rem
            } else "0:$rem"
        }

        i++
    } while (quotient > 0)

    return result
}
