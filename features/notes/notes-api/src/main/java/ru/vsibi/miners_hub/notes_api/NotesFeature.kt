/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.notes_api

import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

interface NotesFeature {

    val navigationContract : NavigationContractApi<NoParams, NoResult>
}