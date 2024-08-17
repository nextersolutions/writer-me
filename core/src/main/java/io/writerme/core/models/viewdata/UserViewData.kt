package io.writerme.core.models.viewdata

import io.writerme.core.common.FormatUtils.EMPTY
import java.util.Date

data class UserViewData(
    val id: String = EMPTY,
    val email: String = EMPTY,
    val firstName: String = EMPTY,
    val lastName: String = EMPTY,
    val username: String = EMPTY,
    val dateOfBirth: Date? = null
) {
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    fun isEmailExist(): Boolean {
        return getFullName().trim().isNotEmpty()
    }

    fun isPersonalInfoExist(): Boolean {
        return firstName.isNotEmpty() && lastName.isNotEmpty() && dateOfBirth != null
    }
}
