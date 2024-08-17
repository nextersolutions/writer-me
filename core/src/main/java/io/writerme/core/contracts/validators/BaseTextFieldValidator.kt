package io.writerme.core.contracts.validators

interface BaseTextFieldValidator {
    val defaultReturnValue: Int?
        get() = null

    /**
     * Validate: input is not empty
     * @return null is valid else return error message
     */
    fun fieldIsNotEmpty(input: String?): Int?
}
