/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.notes_impl

import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult
import ru.vsibi.btc_mathematic.notes_api.NotesFeature
import ru.vsibi.btc_mathematic.notes_impl.presentation.notes.NotesNavigationContract

class NotesFeatureImpl : NotesFeature {

    override val navigationContract: NavigationContractApi<NoParams, NoResult> = NotesNavigationContract

}