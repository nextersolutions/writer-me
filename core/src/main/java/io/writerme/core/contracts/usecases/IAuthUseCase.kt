package io.writerme.core.contracts.usecases

import io.writerme.core.models.dto.base.OperationResult

interface IAuthUseCase {
    suspend fun sendCode(
        phoneNumber: String
    ): OperationResult<Boolean>

    suspend fun verifyOtp(code: String): OperationResult<Boolean>
}
