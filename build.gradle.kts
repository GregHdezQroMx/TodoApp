// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.daggerHilt) apply false
}
true // Needed to make the Suppress annotation work for the plugins block

buildscript {
    // Otras configuraciones del buildscript

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0") // o una versión superior
        // Otras dependencias del buildscript
    }
}