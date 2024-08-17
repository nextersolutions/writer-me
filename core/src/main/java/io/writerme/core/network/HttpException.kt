package io.writerme.core.network

import io.writerme.core.models.dto.base.OperationResult

class HttpException(
    val statusCode: Int,
    val operationResult: OperationResult<String>? = null,
    val url: String? = null,
    cause: Throwable? = null
) : Exception(null, cause)
