package io.writerme.database.localdatasource

import io.realm.kotlin.Realm
import io.writerme.core.common.GlobalConstants.HistoryDefaults.LINK_CHANGES_HISTORY_KEY
import io.writerme.core.common.GlobalConstants.HistoryDefaults.MEDIA_CHANGES_HISTORY_KEY
import io.writerme.core.common.GlobalConstants.HistoryDefaults.TASK_CHANGES_HISTORY_KEY
import io.writerme.core.common.GlobalConstants.HistoryDefaults.TEXT_CHANGES_HISTORY_KEY
import io.writerme.core.common.GlobalConstants.HistoryDefaults.VOICE_CHANGES_HISTORY_KEY
import io.writerme.core.contracts.datasources.local.SettingsLocalDataSource
import io.writerme.core.models.model.Settings
import io.writerme.database.common.DbConst.SETTINGS_ID
import javax.inject.Inject

internal class SettingsLocalDataSourceImpl @Inject constructor(
    private val realm: Realm
) : SettingsLocalDataSource {
    override suspend fun getSettings(): Settings {
        val result = realm.query(Settings::class, "id == $0", SETTINGS_ID).first().find()

        return result
            ?: realm.write {
                val s = Settings().apply {
                    id = SETTINGS_ID
                }
                copyToRealm(s)
            }
    }

    override suspend fun saveName(name: String) {
        val set = getSettings()

        realm.write {
            val settings = findLatest(set)
            settings?.fullName = name
        }
    }

    override suspend fun updateProfileImage(url: String) {
        realm.write {
            val settings = this
                .query(Settings::class, "id == $0", SETTINGS_ID)
                .first()
                .find()

            settings?.profilePictureUrl = url
        }
    }

    override suspend fun setCounter(key: String, value: Int) {
        realm.write {
            val settings = this
                .query(Settings::class, "id == $0", SETTINGS_ID)
                .first()
                .find()

            when (key) {
                MEDIA_CHANGES_HISTORY_KEY -> settings?.mediaChanges = value
                VOICE_CHANGES_HISTORY_KEY -> settings?.voiceChanges = value
                TEXT_CHANGES_HISTORY_KEY -> settings?.textChanges = value
                TASK_CHANGES_HISTORY_KEY -> settings?.taskChanges = value
                LINK_CHANGES_HISTORY_KEY -> settings?.linkChanges = value
            }
        }
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        realm.write {
            val settings = this
                .query(Settings::class, "id == $0", SETTINGS_ID)
                .first()
                .find()

            settings?.isDarkMode = isDarkMode
        }
    }

    override suspend fun setLanguage(language: String) {
        realm.write {
            val settings = this
                .query(Settings::class, "id == $0", SETTINGS_ID)
                .first()
                .find()

            settings?.currentLanguage = language
        }
    }

    override fun close() {
        realm.close()
    }
}
