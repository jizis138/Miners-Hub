dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
rootProject.name = "Momento"
include(":app")
include(":common:core")
include(":common:util")
include(":common:presentation:base")
include(":common:presentation:mvi")
include(":common:presentation:navigation")
include(":common:di")
include(":features:main:main-api")
include(":features:main:main-impl")
include(":features:notes:notes-api")
include(":features:notes:notes-impl")
include(":features:settings:settings-api")
include(":features:settings:settings-impl")
include(":common:data")
include(":common:uikit")
