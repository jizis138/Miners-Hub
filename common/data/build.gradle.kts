plugins {
    `android-common`
    id("kotlin-kapt")
}

dependencies {
    implementation(project(Modules.Common.di))
    implementation(project(Modules.Common.util))
    implementation(project(Modules.Common.core))

    api(Deps.Room.runtime)
    api(Deps.Room.ktx)
    kapt(Deps.Room.compiler)

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.crypto)

}