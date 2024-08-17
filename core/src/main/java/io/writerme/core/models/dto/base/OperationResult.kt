package io.writerme.core.models.dto.base

import com.google.gson.annotations.SerializedName
import io.writerme.core.common.ApiRoutes.ResponseCode.BAD_REQUEST_CODE
import io.writerme.core.common.ApiRoutes.ResponseCode.UNAUTHORIZED_CODE
import io.writerme.core.common.ApiRoutes.ResponseMessages.ERROR
import io.writerme.core.common.ApiRoutes.ResponseMessages.SUCCESS

sealed class OperationResultType {
    data object NetworkConnectionLost : OperationResultType()
    data object Unauthorized : OperationResultType()
    data object InvalidArgumentError : OperationResultType()
}

class OperationResult<T>(

    @SerializedName("code")
    val code: String?,

    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val value: T? = null,

    @SerializedName("error")
    val error: Error? = null,

    val type: OperationResultType? = null
) {

    constructor(message: String? = SUCCESS, value: T?) : this(
        value.toString(),
        true,
        message,
        value,
        null,
        null
    )

    constructor(message: String? = SUCCESS, value: T?, code: String?) : this(
        code = code,
        success = true,
        message = message,
        value = value,
        error = null,
        type = null
    )

    constructor(error: String? = ERROR) : this(
        BAD_REQUEST_CODE,
        null,
        null,
        null,
        Error(code = BAD_REQUEST_CODE.toInt(), description = error),
        null
    )

    constructor(type: OperationResultType?, error: String? = ERROR) : this(
        BAD_REQUEST_CODE,
        null,
        null,
        null,
        Error(code = BAD_REQUEST_CODE.toInt(), description = error),
        type
    )

    constructor(
        error: String? = ERROR,
        code: String? = BAD_REQUEST_CODE,
        type: OperationResultType? = null
    ) : this(
        code,
        null,
        null,
        null,
        Error(code = BAD_REQUEST_CODE.toInt(), description = error),
        type
    )

    fun hasError(): Boolean {
        return error != null
    }

    fun isConnectionLost(): Boolean {
        return type == OperationResultType.NetworkConnectionLost
    }

    fun hasUnAuthorizedError(): Boolean {
        return code == UNAUTHORIZED_CODE ||
            error?.code == UNAUTHORIZED_CODE.toInt() ||
            type == OperationResultType.Unauthorized
    }
    data class Error(
        @SerializedName("code")
        val code: Int?,
        @SerializedName("description")
        val description: String?
    )
}
