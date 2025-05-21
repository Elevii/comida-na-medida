// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt) apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.0" apply false
}
