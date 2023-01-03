plugins {
    `android-common`
    `kotlinx-serialization`
}



dependencies {
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.fragment)
//    implementation(Deps.AndroidX.lifecycleCommon)
    implementation(Deps.Android.material)
    api(Deps.adapterDelegates)
    implementation(Deps.Coroutines.core)
    implementation(Deps.serializationJson)
}