package io.writerme.core.common

import io.writerme.core.BuildConfig

object ApiRoutes {
    private const val PRODUCTION = "production"
    private const val BETA = "beta"

    // TODO: unknown yet, update
    private const val BASE_URL = "https://writerme.io"
    private const val BASE_URL_DEV = "https://dev.writerme.io"
    private const val BASE_URL_BETA = "https://staging.writerme.io"

    object ResponseMessages {
        const val SUCCESS = "Success"
        const val ERROR = "Please check your internet connection and try again."
    }

    object ResponseCode {
        const val UNAUTHORIZED_CODE = "403"
        const val BAD_REQUEST_CODE = "400"
    }

    fun getBaseUrl(): String {
        return when (BuildConfig.STAGE) {
            PRODUCTION -> BASE_URL
            BETA -> BASE_URL_BETA
            else -> BASE_URL_DEV
        }
    }
}
