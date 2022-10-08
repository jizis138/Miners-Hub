package ru.vsibi.momento.notes_impl.presentation.notes

import ru.vsibi.momento.navigation.contract.NavigationContract
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult

object NotesNavigationContract : NavigationContract<NoParams, NoResult>(NotesFragment::class) {
}