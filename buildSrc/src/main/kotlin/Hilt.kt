object Hilt {
    const val version = "2.46"
    const val hiltAndroid = "com.google.dagger:hilt-android:$version"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
    const val hiltCore = "com.google.dagger:hilt-core:$version"

    private const val hiltNavigationVersion = "1.0.0"
    const val hiltNavigation = "androidx.hilt:hilt-navigation:$hiltNavigationVersion"
    const val hiltFragmentsNavigation =
        "androidx.hilt:hilt-navigation-fragment:$hiltNavigationVersion"

    private const val javaInjectVersion = "1"
    const val javaInject = "javax.inject:javax.inject:$javaInjectVersion"

}
