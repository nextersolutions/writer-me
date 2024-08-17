package io.writerme.core.extensions

import io.writerme.core.common.FormatUtils.ZERO

inline fun <reified T : Enum<T>> String.findEnumValueByName(enumClass: Class<T>): T? {
    return enumClass.enumConstants?.firstOrNull { it.name == this }
}

inline fun <reified T : Enum<T>> String.findEnumValueByNameOrDefault(enumClass: Class<T>): T {
    val items = enumClass.enumConstants.orEmpty()
    return items.firstOrNull { it.name == this } ?: items.first()
}

inline fun <reified T : Enum<T>> Int?.findOrDefault(enumClass: Class<T>): T {
    val items = enumClass.enumConstants.orEmpty()
    require(items.isNotEmpty()) {
        "Invalid enum value"
    }
    return items.firstOrNull { it.ordinal == (this ?: ZERO) } ?: items.first()
}

inline fun <reified T : Enum<T>> Int?.findOrNull(enumClass: Class<T>): T? {
    return enumClass.enumConstants?.firstOrNull { it.ordinal == this }
}
