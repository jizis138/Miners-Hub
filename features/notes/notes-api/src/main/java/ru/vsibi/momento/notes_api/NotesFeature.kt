/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.notes_api

import ru.vsibi.momento.navigation.contract.NavigationContractApi
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult

interface NotesFeature {

    val navigationContract : NavigationContractApi<NoParams, NoResult>
}