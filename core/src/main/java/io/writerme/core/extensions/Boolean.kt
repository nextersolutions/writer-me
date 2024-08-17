package io.writerme.core.extensions

fun Boolean?.orDefault(defaultValue: Boolean = false): Boolean {
    return this ?: defaultValue
}
