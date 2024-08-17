// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.navigation) apply false
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.ktLint) apply true
    alias(libs.plugins.hilt) apply false
}

tasks.register<Delete>("clean") {
    delete(project.layout.buildDirectory)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configureKtLint()
}

ktlint {
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.JSON)
    }
}

fun configureKtLint() {
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
        android.set(true)
        ignoreFailures.set(true)
        baseline.set(file("config/ktlint/baseline.xml"))
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}

/**
 * Task to run before pushing a commit. Analyses all kotlin files and fails if any new issues appear.
 */
tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektAll") {
    val projectSource = file(projectDir)
    val configFile = files("$rootDir/config/detekt/detekt.yml")
    val baselineFile = file("$rootDir/config/detekt/baseline.yml")

    description = "Running Detekt for all modules"
    setSource(projectSource)
    config.setFrom(configFile)
    baseline.set(baselineFile)
    ignoreFailures = false
    reports {
        html.outputLocation.set(file("$rootDir/reports/detekt.html"))
    }
    include("**/*.kt")
    include("**/*.kts")
    exclude("resources/")
    exclude("build/")
}

/**
 * Task to run first time we use detekt. Generates a baseline file which is used as reference from
 * henceforth to fail for only new issues.
 */
tasks.register<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>("detektAllBaseline") {
    val projectSource = file(projectDir)
    val configFile = files("$rootDir/config/detekt/detekt.yml")
    val baselineFile = file("$rootDir/config/detekt/baseline.yml")

    description = "Running Detekt Baseline for all modules"
    setSource(projectSource)
    config.setFrom(configFile)
    baseline.set(baselineFile)
    include("**/*.kt")
    include("**/*.kts")
    exclude("resources/")
    exclude("build/")
}
