package io.writerme.core.network

import io.writerme.core.models.dto.base.OperationResult

interface HttpResponse {

    val statusCode: Int

    val operationResult: OperationResult<*>?

    val url: String?
}
