package io.writerme.core.contracts.repositories

import io.writerme.core.models.dto.auth.request.CreateAccountRequestDto
import io.writerme.core.models.dto.auth.request.VerifyOtpRequestDto
import io.writerme.core.models.dto.base.OperationResult
import java.io.File

interface IUserRepository {
    suspend fun sendCode(
        request: CreateAccountRequestDto
    ): OperationResult<Boolean>

    suspend fun verifyOtp(
        requestDto: VerifyOtpRequestDto
    ): OperationResult<Boolean>

    suspend fun updateAvatarImage(file: File): OperationResult<Boolean>

    suspend fun logout(): OperationResult<Boolean>

    suspend fun deleteAccount(): OperationResult<Boolean>
}
