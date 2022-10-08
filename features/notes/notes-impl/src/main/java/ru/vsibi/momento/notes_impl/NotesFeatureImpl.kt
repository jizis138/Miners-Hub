/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.notes_impl

import ru.vsibi.momento.navigation.contract.NavigationContractApi
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult
import ru.vsibi.momento.notes_api.NotesFeature
import ru.vsibi.momento.notes_impl.presentation.notes.NotesNavigationContract

class NotesFeatureImpl : NotesFeature {

    override val navigationContract: NavigationContractApi<NoParams, NoResult> = NotesNavigationContract

}