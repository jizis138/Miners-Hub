object Modules {

    const val app = ":app"

    object Common {
        const val core = ":common:core"
        const val data = ":common:data"
        const val util = ":common:util"
        const val di = ":common:di"
        const val presentation = ":common:presentation:base"
        const val navigation = ":common:presentation:navigation"
        const val mvi = ":common:presentation:mvi"
        const val uikit = ":common:uikit"
        const val network = ":common:network"
    }

    class FeatureTemplate(name: String) {
        val api = ":features:$name:$name-api"
        val impl = ":features:$name:$name-impl"
    }

    object Feature {
        val main = FeatureTemplate("main")
        val notes = FeatureTemplate("notes")
        val settings = FeatureTemplate("settings")
        val knowledge = FeatureTemplate("knowledge")
        val mining = FeatureTemplate("mining")
        val exchangeRate = FeatureTemplate("exchange-rate")
    }
}