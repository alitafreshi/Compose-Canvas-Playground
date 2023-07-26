object Compose {
    //COMPOSE BOOM DEPENDENCY
    private const val compose_boom_version = "2023.06.01"
    const val composeBoom = "androidx.compose:compose-bom:$compose_boom_version"

    //COMPOSE DEPENDENCIES
    const val compose_material_3 = "androidx.compose.material3:material3"
    const val compose_preview = "androidx.compose.ui:ui-tooling-preview"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling"
    const val compose_ui_graphics = "androidx.compose.ui:ui-graphics"
    const val compose_ui_manifest = "androidx.compose.ui:ui-test-manifest"
    const val compose_ui_util = "androidx.compose.ui:ui-util"

    private const val compose_activity_version = "1.7.2"
    const val compose_activity = "androidx.activity:activity-compose:$compose_activity_version"

    private const val compose_viewMode_version = "2.6.1"
    const val compose_viewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:$compose_viewMode_version"

    private const val compose_constraint_layout_version = "1.0.1"
    const val compose_constraint_layout =
        "androidx.constraintlayout:constraintlayout-compose:$compose_constraint_layout_version"


    //COMPOSE COMPILER DEPENDENCY
    const val compose_compiler_version = "1.4.6"
    const val compose_compiler = "androidx.compose.compiler:compiler:$compose_compiler_version"

    //COMPOSE VIEWBINDING
    const val compose_view_binding_version = "1.4.3"
    const val compose_view_binding =
        "androidx.compose.ui:ui-viewbinding:$compose_view_binding_version"
}
