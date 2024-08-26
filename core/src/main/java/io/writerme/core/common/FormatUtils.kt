package io.writerme.core.common

object FormatUtils {
    const val space = " "
    const val dot = "."
    const val plus = "+"
    const val minus = "-"
    const val star = "*"
    const val dollar = "$"
    const val tag = "tag"
    const val EMPTY = ""
    const val ZERO = 0
    const val VALUE_0_5 = 0.5f
    const val VALUE_1 = 1
    const val VALUE_1_5 = 1.5f
    const val VALUE_2 = 2
    const val VALUE_3 = 3
    const val VALUE_4 = 4
    const val VALUE_5 = 5
    const val VALUE_6 = 6
    const val VALUE_7 = 7
    const val VALUE_8 = 8
    const val VALUE_9 = 9
    const val VALUE_10 = 10
    const val VALUE_50 = 50
    const val VALUE_60 = 60
    const val VALUE_100 = 100
    const val VALUE_140 = 140
    const val VALUE_180 = 180
    const val VALUE_260 = 260
    const val VALUE_350 = 350
    const val VALUE_360 = 360
    const val SECOND: Long = 1000
    const val OTP_CODE_LENGTH = 5
    const val PASSWORD_MIN_LENGTH = 6
    const val PASSCODE_DIGITS_NUMBER = 4
    const val phoneMinLength = 9
    const val phoneOtpLength = 6
    const val phoneMaxLength = 13
    const val nameMaxLength = 20
    const val nameMinLength = 2
    const val cardLength = 16
    const val animationDuration = 300

    fun getNumeric(value: String, length: Int): String {
        if (value.isNotEmpty() && value.length >= length) {
            return value.replace(Patterns.numericFormatRegex.toRegex(), EMPTY)
                .substring(ZERO, length)
        }
        return value.replace(Patterns.numericFormatRegex.toRegex(), EMPTY)
    }

    object Patterns {
        const val priceFormat = "#,###"
        const val timeHHMMSS = "HH:mm:ss"
        const val timeHHMM = "HH:mm"
        const val timeAmPmFormat = "hh:mm a"
        const val birthDateFormat = "MM/dd/yyyy"
        const val dateFormat = "MMMM d, yyyy"
        const val monthDate = "MMM d"
        const val emailRegex = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
            ")+"
        const val passwordRegex = "^(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$"
        const val numericFormatRegex = "[^0-9]"
        const val numberDUNSIsValidRegex = "\\d+"
        const val linkRegex =
            "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    }

    fun clearOtpFormat(phone: String): String {
        val pattern = Patterns.numericFormatRegex.toRegex()
        if (phone.isNotEmpty() && phone.length >= phoneOtpLength) {
            return phone.replace(pattern, EMPTY).substring(ZERO, phoneOtpLength)
        }
        return phone.replace(pattern, EMPTY)
    }

    object MimeType {
        const val image = "image/*"
        const val textPlain = "text/plain"
        const val tel = "tel:"
    }
}
