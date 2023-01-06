plugins {
    `android-common`
    `kotlin-kapt`
    `kotlinx-serialization`
}

android {
    enableViewBinding()
}

dependencies {
    implementation(project(Modules.Feature.mining.api))
    implementation(project(Modules.Feature.knowledge.api))

    implementation(project(Modules.Common.core))
    implementation(project(Modules.Common.di))
    implementation(project(Modules.Common.presentation))
    implementation(project(Modules.Common.util))
    implementation(project(Modules.Common.uikit))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Common.network))
    kapt(Deps.Room.compiler)
    implementation(Deps.serializationJson)
    implementation(Deps.AndroidX.swipeRefreshLayout)


}