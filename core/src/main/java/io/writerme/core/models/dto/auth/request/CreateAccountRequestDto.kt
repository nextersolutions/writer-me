package io.writerme.core.models.dto.auth.request

data class CreateAccountRequestDto(
    val phoneNumber: String,
    val countryCode: String,
    val fakeData: Boolean
)
