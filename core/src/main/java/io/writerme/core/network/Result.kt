package io.writerme.core.network

import io.writerme.core.common.ApiRoutes
import io.writerme.core.models.dto.base.OperationResult

sealed class Result<out T> {

    sealed class Success<T> : Result<T>() {

        abstract val value: T

        override fun toString(): String = "Success($value)"

        class Value<T>(override val value: T) : Success<T>()

        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val operationResult: OperationResult<*>? = null,
            override val url: String? = null
        ) : Success<T>(), io.writerme.core.network.HttpResponse

        object Empty : Success<Nothing>() {

            override val value: Nothing get() = error("No value")

            override fun toString(): String = ApiRoutes.ResponseMessages.SUCCESS
        }
    }

    sealed class Failure<E : Throwable>(open val error: E? = null) : Result<Nothing>() {

        override fun toString(): String = "Failure($error)"

        class Error(override val error: Throwable) : Failure<Throwable>(error)

        class HttpError(override val error: HttpException) :
            Failure<HttpException>(),
            HttpResponse {

            override val statusCode: Int get() = error.statusCode

            override val operationResult: OperationResult<*>? get() = error.operationResult

            override val url: String? get() = error.url
        }
    }
}

typealias EmptyResult = Result<Nothing>
