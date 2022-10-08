package ru.vsibi.momento.notes_impl.presentation.add_new_note

import ru.vsibi.momento.navigation.contract.NavigationContract
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult

object AddNewNoteNavigationContract : NavigationContract<NoParams, NoResult>(AddNewNoteFragment::class)