plugins {
    `android-common`
    `kotlinx-serialization`

}

dependencies {
    implementation(project(Modules.Common.navigation))
    implementation(project(Modules.Common.util))

    implementation(Deps.serializationJson)

    implementation(Deps.Coroutines.core)

}