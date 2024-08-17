package io.writerme.domain.validators

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import io.writerme.core.contracts.validators.IPhoneValidator
import javax.inject.Inject

internal class PhoneValidatorImpl @Inject constructor(
    private val phoneNumberUtil: PhoneNumberUtil
) : IPhoneValidator {
    override fun isValidNumberForRegion(fullPhoneNumber: String, countryCode: String): Boolean {
        val number = phoneNumberUtil.parse(
            fullPhoneNumber,
            Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name
        )
        return phoneNumberUtil.isValidNumberForRegion(number, countryCode.uppercase())
    }
}
