/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl

import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult
import ru.vsibi.miners_hub.notes_api.NotesFeature
import ru.vsibi.miners_hub.notes_impl.presentation.notes.NotesNavigationContract

class NotesFeatureImpl : NotesFeature {

    override val navigationContract: NavigationContractApi<NoParams, NoResult> = NotesNavigationContract

}