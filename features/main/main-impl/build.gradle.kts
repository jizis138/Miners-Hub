plugins {
    `android-common`
}

android {
    enableViewBinding()
}

dependencies {
    implementation(project(Modules.Feature.main.api))
    implementation(project(Modules.Feature.notes.api))
    implementation(project(Modules.Feature.settings.api))
    implementation(project(Modules.Feature.knowledge.api))
    implementation(project(Modules.Feature.mining.api))
    implementation(project(Modules.Feature.exchangeRate.api))

    implementation(project(Modules.Common.core))
    implementation(project(Modules.Common.di))
    implementation(project(Modules.Common.presentation))
    implementation(project(Modules.Common.util))
    implementation(project(Modules.Common.uikit))

}