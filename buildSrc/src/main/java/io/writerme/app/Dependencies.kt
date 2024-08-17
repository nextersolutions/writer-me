package io.writerme.app

object Dependencies {

    object Modules {
        val core = mapOf("path" to ":core")
        val resources = mapOf("path" to ":resources")
        val network = mapOf("path" to ":network")
        val domain = mapOf("path" to ":domain")
        val data = mapOf("path" to ":data")

        object Features {
            private const val moduleRoot = ":features"
            val onboarding = mapOf("path" to "$moduleRoot:onboarding")
            val home = mapOf("path" to "$moduleRoot:home")
            val signup = mapOf("path" to "$moduleRoot:signup")
            val profile = mapOf("path" to "$moduleRoot:profile")
            val userSettings = mapOf("path" to "$moduleRoot:user-settings")
        }
    }
}