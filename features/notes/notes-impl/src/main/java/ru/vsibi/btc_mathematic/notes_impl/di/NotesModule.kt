/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.notes_impl.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.btc_mathematic.data.di.qualifier.StorageQualifier
import ru.vsibi.btc_mathematic.notes_api.NotesFeature
import ru.vsibi.btc_mathematic.notes_impl.NotesFeatureImpl
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.factory
import ru.vsibi.btc_mathematic.di.single
import ru.vsibi.btc_mathematic.di.viewModel
import ru.vsibi.btc_mathematic.notes_impl.data.repo_impl.NotesRepoImpl
import ru.vsibi.btc_mathematic.notes_impl.data.storage.NotesStorage
import ru.vsibi.btc_mathematic.notes_impl.domain.logic.NotesInteractor
import ru.vsibi.btc_mathematic.notes_impl.domain.repo.NotesRepo
import ru.vsibi.btc_mathematic.notes_impl.presentation.add_new_note.AddNewNoteViewModel
import ru.vsibi.btc_mathematic.notes_impl.presentation.notes.NotesViewModel

object NotesModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory(::NotesFeatureImpl) bindSafe NotesFeature::class
    }

    private fun createDataModule() = module {
        single(::NotesRepoImpl) bindSafe NotesRepo::class
        single {
            NotesStorage(
                databaseFactory = get(named(StorageQualifier.Simple)),
            )
        }
    }

    private fun createDomainModule() = module {
        factory(::NotesInteractor)

    }

    private fun createPresentationModule() = module {
        viewModel(::NotesViewModel)
        viewModel(::AddNewNoteViewModel)
    }
}