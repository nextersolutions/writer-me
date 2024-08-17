package io.writerme.domain.validators

import io.writerme.core.common.FormatUtils.OTP_CODE_LENGTH
import io.writerme.core.common.FormatUtils.PASSWORD_MIN_LENGTH
import io.writerme.core.common.FormatUtils.Patterns.emailRegex
import io.writerme.core.common.FormatUtils.cardLength
import io.writerme.core.common.FormatUtils.nameMaxLength
import io.writerme.core.common.FormatUtils.nameMinLength
import io.writerme.core.common.FormatUtils.phoneMaxLength
import io.writerme.core.common.FormatUtils.phoneMinLength
import io.writerme.core.contracts.validators.IFieldValidator
import io.writerme.core.contracts.validators.IPhoneValidator
import io.writerme.core.extensions.isNumeric
import javax.inject.Inject
import io.writerme.resources.R as ResR

class FieldValidatorImpl @Inject constructor(
    private val phoneValidator: IPhoneValidator
) : IFieldValidator {

    override fun fieldIsNotEmpty(input: String?): Int? {
        if (input.isNullOrEmpty()) return ResR.string.error_input_is_empty

        return defaultReturnValue
    }

    override fun phoneIsValid(phone: String, fullPhoneNumber: String, countryCode: String): Int? {
        if (phone.length > phoneMaxLength) {
            return ResR.string.error_phone_length_up_invalid
        }
        if (phone.length < phoneMinLength) {
            return ResR.string.error_phone_length_down_invalid
        }
        if (!phone.isNumeric()) {
            return ResR.string.error_phone_invalid_number
        }
        return try {
            if (phoneValidator.isValidNumberForRegion(fullPhoneNumber, countryCode).not()) {
                return defaultReturnValue
            } else {
                return ResR.string.error_phone_invalid_number
            }
        } catch (_: Exception) {
            defaultReturnValue
        }
    }

    override fun emailIsValid(email: String?): Int? {
        return if (email.isNullOrEmpty().not() && email?.matches(emailRegex.toRegex()) == true) {
            defaultReturnValue
        } else {
            ResR.string.error_email_is_not_valid
        }
    }

    override fun passwordIsValid(password: String?): Int? {
        return if (
            !password.isNullOrEmpty() && password.length >= PASSWORD_MIN_LENGTH
        ) {
            defaultReturnValue
        } else {
            ResR.string.error_password_is_not_valid
        }
    }

    override fun otpCodeIsValid(code: String): Int? {
        if (code.isEmpty()) return ResR.string.error_input_is_empty

        if (code.length != OTP_CODE_LENGTH) return ResR.string.error_invalid_operation

        return defaultReturnValue
    }

    override fun nameIsValid(value: String, isOptional: Boolean): Int? {
        if (value.isEmpty() && isOptional.not()) return ResR.string.error_input_is_empty
        if (value.length < nameMinLength) return ResR.string.error_invalid_operation
        if (value.length > nameMaxLength) return ResR.string.error_invalid_operation

        return defaultReturnValue
    }

    override fun confirmPasswordValid(password: String, confirmPassword: String): Int? {
        if (password.isEmpty() ||
            password != confirmPassword
        ) {
            return ResR.string.error_confirm_password_is_not_valid
        }

        return defaultReturnValue
    }

    override fun cardIsValid(card: String): Int? {
        if (card.isEmpty()) return ResR.string.error_input_is_empty
        if (card.length < cardLength) ResR.string.error_card_length

        return defaultReturnValue
    }
}
