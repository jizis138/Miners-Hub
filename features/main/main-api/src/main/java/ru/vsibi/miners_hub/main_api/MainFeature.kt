/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.main_api

import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

interface MainFeature {

    val mainNavigationContract: NavigationContractApi<NoParams, NoResult>

    enum class TabType {
        Notes,
        Settings,
        Knowledge
    }

    fun returnToMainScreenAndOpenTab(tab: TabType)

}