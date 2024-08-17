package io.writerme.core.extensions

import io.writerme.core.common.FormatUtils.Patterns.priceFormat
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.common.FormatUtils.ZERO
import io.writerme.core.common.FormatUtils.dollar
import io.writerme.core.common.FormatUtils.dot
import io.writerme.core.common.FormatUtils.minus
import io.writerme.core.common.FormatUtils.plus
import java.text.DecimalFormat

fun Double.toFormattedAmount(): Pair<String, String> {
    val parts = String.format("%.2f", this).split(dot)
    val integerPart = parts[ZERO]
    val decimalPart = parts.getOrElse(VALUE_1) { "$ZERO" }
    val formatter = DecimalFormat(priceFormat)
    val formattedIntegerPart = formatter.format(integerPart.toLong())
    return Pair(formattedIntegerPart, decimalPart)
}

fun Double.toAmountWithDollar(): String {
    val result = this.toFormattedAmount()
    return "$dollar${result.first}$dot${result.second}"
}

fun Double.toTransactionAmount(isReceiving: Boolean): String {
    val mark = if (isReceiving) plus else minus
    val result = this.toFormattedAmount()

    return "$mark ${result.first}$dot${result.second}"
}
