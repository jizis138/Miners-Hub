package ru.vsibi.miners_hub.notes_impl.presentation.notes

import ru.vsibi.miners_hub.navigation.contract.NavigationContract
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

object NotesNavigationContract : NavigationContract<NoParams, NoResult>(NotesFragment::class) {
}