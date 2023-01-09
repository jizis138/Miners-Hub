/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.main_api

import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult

interface MainFeature {

    val mainNavigationContract: NavigationContractApi<NoParams, NoResult>

    enum class TabType {
        Rates,
        Mining,
        Settings,
        Knowledge
    }

    fun returnToMainScreenAndOpenTab(tab: TabType)

}