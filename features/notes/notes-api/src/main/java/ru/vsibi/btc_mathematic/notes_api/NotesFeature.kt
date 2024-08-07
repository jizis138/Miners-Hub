/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.notes_api

import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult

interface NotesFeature {

    val navigationContract : NavigationContractApi<NoParams, NoResult>
}