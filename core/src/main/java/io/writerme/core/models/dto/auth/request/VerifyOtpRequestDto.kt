package io.writerme.core.models.dto.auth.request

data class VerifyOtpRequestDto(
    val phoneNumber: String,
    val countryCode: String,
    val verificationCode: String,
    val fakeData: Boolean
)
