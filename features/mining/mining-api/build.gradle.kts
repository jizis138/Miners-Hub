plugins {
    `android-common`
}

dependencies {
    implementation(project(Modules.Feature.knowledge.api))
    implementation(project(Modules.Common.navigation))
    implementation(project(Modules.Common.util))
}