package io.writerme.core.common

import io.writerme.core.BuildConfig

object GlobalConstants {
    private val packageId = if (BuildConfig.DEBUG) {
        "io.writerme.app.debug"
    } else {
        "io.writerme.app"
    }

    val contentProviderAuthority = "$packageId.provider"
    const val animationDuration = 300
    const val animationDurationLong = 1000

    val isDev = BuildConfig.DEBUG
    const val isStaging = BuildConfig.STAGE == "beta"

    object HistoryDefaults {
        var MEDIA_CHANGES_HISTORY = 3
        var VOICE_CHANGES_HISTORY = 2
        var TEXT_CHANGES_HISTORY = 5
        var TASK_CHANGES_HISTORY = 3
        var LINK_CHANGES_HISTORY = 3

        const val MEDIA_CHANGES_HISTORY_KEY = "media_changes"
        const val VOICE_CHANGES_HISTORY_KEY = "voice_changes"
        const val TEXT_CHANGES_HISTORY_KEY = "text_changes"
        const val TASK_CHANGES_HISTORY_KEY = "task_changes"
        const val LINK_CHANGES_HISTORY_KEY = "link_changes"
    }

    const val DB_SCHEMA_VERSION: Long = 0

    const val DB_NAME: String = "writer.realm"

    val SUPPORTED_LANGUAGES = listOf("English", "Українська", "Deutsch", "Español", "Française", "Русский")

    object AppLink {
        const val terms = "https://google.com"
        const val privacyPolicy = "https://google.com"
        const val eSign = "https://google.com"
        const val linkTag = "URL"
    }

    object BundleKey {
        const val AMOUNT_KEY = "AMOUNT_KEY"
        const val FEE_KEY = "FEE_KEY"
        const val CARD_KEY = "CARD_KEY"
    }

    object ApiFields {
        const val twilio = "twilio"
        const val status = "status"
        const val to = "to"
        const val channel = "channel"
    }

    fun getPackageName(): String = packageId

    object Ping {
        const val host = "8.8.8.8"
        const val port = 53
        const val responseTime = 15000
        const val observeTime = 5000L
    }
}
