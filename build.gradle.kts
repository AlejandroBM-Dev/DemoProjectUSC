plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinxSerialization) apply false

    //SQLDelight
    id("app.cash.sqldelight").version("2.0.2").apply(false)
    alias(libs.plugins.ksp) apply false

}

/**
 * Al ejecutar la tarea clean, Gradle utilizará esta configuración para eliminar el directorio de construcción del proyecto raíz.
 * **/
tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}