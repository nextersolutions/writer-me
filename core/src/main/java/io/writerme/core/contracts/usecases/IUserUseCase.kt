package io.writerme.core.contracts.usecases

import android.net.Uri
import io.writerme.core.models.dto.base.OperationResult
import io.writerme.core.models.viewdata.UserViewData
import java.util.Date

interface IUserUseCase {
    suspend fun getUser(): OperationResult<UserViewData>

    suspend fun addEmail(email: String): OperationResult<Boolean>
    suspend fun addPersonalInfo(
        firstName: String,
        lastName: String,
        dateOfBirth: Date
    ): OperationResult<Boolean>

    suspend fun updateUser(
        user: UserViewData
    ): OperationResult<Boolean>

    /*suspend fun updatePhoneNumber(
        phone: String
    ) : OperationResult<Boolean>*/

    suspend fun updateAvatarImage(
        uri: Uri
    ): OperationResult<Boolean>

    suspend fun logout(): OperationResult<Boolean>

    suspend fun deleteAccount(): OperationResult<Boolean>
}
