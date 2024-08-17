package io.writerme.core.models.dto.auth.response

import com.google.gson.annotations.SerializedName
import io.writerme.core.common.GlobalConstants.ApiFields

data class SendCodeResponseDto(
    @SerializedName(ApiFields.twilio)
    val twilio: TwilioDto
)

data class TwilioDto(
    @SerializedName(ApiFields.status)
    val status: String,

    @SerializedName(ApiFields.to)
    val to: String,

    @SerializedName(ApiFields.channel)
    val channel: String
)
