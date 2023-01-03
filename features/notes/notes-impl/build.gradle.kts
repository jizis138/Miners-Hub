plugins {
    `android-common`
    `kotlin-kapt`
//    `kotlinx-serialization`
}

android {
    enableViewBinding()
}

dependencies {
    implementation(project(Modules.Feature.notes.api))

    implementation(project(Modules.Common.core))
    implementation(project(Modules.Common.di))
    implementation(project(Modules.Common.presentation))
    implementation(project(Modules.Common.util))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Common.uikit))

    kapt(Deps.Room.compiler)

}