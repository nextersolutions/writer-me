package io.writerme.app

object Config {
    private const val major = "1"
    private const val minor = "1"
    private const val path = "4"
    private const val currentTask = "4"
    const val versionName = "$major.$minor.${path}_($currentTask)"

    const val stage = BuildStage.DEV

    const val applicationId = "io.writerme.app"


    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    object Modules {
        const val core = "io.writerme.core"
        const val data = "io.writerme.data"
        const val database = "io.writerme.database"
        const val resources = "io.writerme.resources"
        const val domain = "io.writerme.domain"
        const val network = "io.writerme.network"

        object Features {
            const val onboarding = "io.writerme.features.onboarding"
            const val home = "io.writerme.features.home"
        }
    }
}