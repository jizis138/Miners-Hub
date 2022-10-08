/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.main_api

import ru.vsibi.momento.navigation.contract.NavigationContractApi
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult

interface MainFeature {

    val mainNavigationContract: NavigationContractApi<NoParams, NoResult>

    enum class TabType {
        Notes,
        Settings
    }

    fun returnToMainScreenAndOpenTab(tab: TabType)

}