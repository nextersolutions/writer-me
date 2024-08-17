package io.writerme.core.contracts.validators

interface IPhoneValidator {
    fun isValidNumberForRegion(fullPhoneNumber: String, countryCode: String): Boolean
}
