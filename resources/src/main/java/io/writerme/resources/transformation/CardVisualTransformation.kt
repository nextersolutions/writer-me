package io.writerme.resources.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import io.writerme.core.common.FormatUtils.ZERO

class CardVisualTransformation : VisualTransformation {
    private val mask: String = "0000 0000 0000 0000"
    private val maskNumber: Char = '0'
    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text
        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = ZERO
            var textIndex = ZERO

            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, CardOffsetMapper(mask, numberChar = maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardVisualTransformation) return false
        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class CardOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = ZERO
        var i = ZERO
        while (i < offset + noneDigitCount) {
            if (i < mask.length) {
                if (mask[i++] != numberChar) noneDigitCount++
            }
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int {
        return offset - mask.take(offset).count { it != numberChar }
    }
}
