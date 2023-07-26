object Build {
    private const val gradleBuildTools = "8.0.2"
    const val buildTools = "com.android.tools.build:gradle:${gradleBuildTools}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val kaptGradlePlugin = "org.jetbrains.kotlin.kapt:${Kotlin.version}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.version}"
    const val kotlinSerializationGradlePlugin =
        "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
    private const val googleServicesPluginVersion = "4.3.15"
    const val googleServicesPlugin = "com.google.gms:google-services:$googleServicesPluginVersion"
}
