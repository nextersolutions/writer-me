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
