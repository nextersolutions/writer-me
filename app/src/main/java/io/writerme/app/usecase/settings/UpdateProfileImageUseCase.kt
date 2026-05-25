package io.writerme.app.usecase.settings

import io.writerme.app.data.repository.SettingsRepository
import io.writerme.app.utils.FilesUtil
import javax.inject.Inject

interface UpdateProfileImageUseCase {
    suspend operator fun invoke(url: String)
}

class UpdateProfileImageUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val filesUtil: FilesUtil
) : UpdateProfileImageUseCase {
    override suspend fun invoke(url: String) {
        val uri = filesUtil.writeImageToFile(url)
        uri?.let { settingsRepository.updateProfileImage(it) }
    }
}
