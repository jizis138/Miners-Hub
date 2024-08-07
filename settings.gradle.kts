dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
rootProject.name = "BTC Mathematic"
include(":app")
include(":common:core")
include(":common:util")
include(":common:presentation:base")
include(":common:presentation:mvi")
include(":common:presentation:navigation")
include(":common:di")
include(":features:main:main-api")
include(":features:main:main-impl")
include(":features:settings:settings-api")
include(":features:settings:settings-impl")
include(":common:data")
include(":common:uikit")
include(":features:knowledge:knowledge-api")
include(":features:knowledge:knowledge-impl")
include(":common:network")
include(":features:mining:mining-api")
include(":features:mining:mining-impl")
include(":features:exchange-rate:exchange-rate-api")
include(":features:exchange-rate:exchange-rate-impl")
