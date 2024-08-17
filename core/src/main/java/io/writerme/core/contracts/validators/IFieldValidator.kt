package io.writerme.core.contracts.validators

interface IFieldValidator : BaseTextFieldValidator {
    /**
     * Validate: phone
     * @return null is valid else return error message
     */
    fun phoneIsValid(phone: String, fullPhoneNumber: String, countryCode: String): Int?

    /**
     * Validate: email
     * @return null is valid else return error message
     */
    fun emailIsValid(email: String?): Int?

    /**
     * Validate: password
     * @return null is valid else return error message
     */
    fun passwordIsValid(password: String?): Int?

    /**
     * Validate: otp code
     * @return null is valid else return error message
     */
    fun otpCodeIsValid(code: String): Int?

    /**
     * Validate: Name
     * @return null is valid else return error message
     */
    fun nameIsValid(value: String, isOptional: Boolean): Int?

    /**
     * Validate: Confirm password
     * @return null is valid else return error message
     */
    fun confirmPasswordValid(password: String, confirmPassword: String): Int?

    /**
     * Validate: Card
     * @return null is valid else return error message
     */
    fun cardIsValid(card: String): Int?
}
