plugins {
    `android-common`
}

android {
    enableViewBinding()
}

dependencies {
    implementation(project(Modules.Common.util))
    
    implementation(Deps.Android.material)


}