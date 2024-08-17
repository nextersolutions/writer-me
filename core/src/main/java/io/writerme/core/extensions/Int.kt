package io.writerme.core.extensions

import io.writerme.core.common.FormatUtils.dollar

fun Int.withDollar(): String {
    return "$dollar$this"
}
