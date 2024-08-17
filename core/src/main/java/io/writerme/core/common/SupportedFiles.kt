package io.writerme.core.common

import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.dot

object SupportedFiles {
    object Images {
        const val JPEG = ".jpeg"
        const val JPG = ".jpg"
        const val PNG = ".png"
    }

    fun isSupportedImage(value: String): Boolean {
        val supported = listOf(Images.JPEG, Images.JPG, Images.PNG)
        return clearDots(supported).any { value.endsWith(it, true) }
    }

    private fun clearDots(items: List<String>): List<String> = items.map { it.replace(dot, EMPTY) }
}
