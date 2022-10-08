/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.momento.main_impl.presentation.main

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import ru.vsibi.momento.core.environment.Environment
import ru.vsibi.momento.main_api.MainFeature
import ru.vsibi.momento.main_impl.domain.logic.OuterTabNavigator
import ru.vsibi.momento.mvi.BaseViewModel
import ru.vsibi.momento.mvi.provideSavedState
import ru.vsibi.momento.navigation.RootRouter
import ru.vsibi.momento.navigation.model.RequestParams
import ru.vsibi.momento.navigation.model.createParams
import ru.vsibi.momento.navigation.model.requestParams
import ru.vsibi.momento.notes_api.NotesFeature
import ru.vsibi.momento.settings_api.SettingsFeature
import java.util.*

class MainViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    savedStateHandle: SavedStateHandle,
    private val outerTabNavigator: OuterTabNavigator,
    private val environment: Environment,
    private val features : Features
) : BaseViewModel<MainState, MainEvent>(router, requestParams) {

    class Features(
        val notesFeature: NotesFeature,
        val settingsFeature: SettingsFeature
    )

    private val fragmentsStack = Stack<MainFeature.TabType>()

//    private val debugLauncher = registerScreen(features.debugFeature.navigationContract)

    init {
        val savedState = savedStateHandle.provideSavedState { SavedState(tabsStack = fragmentsStack) }
        val tabsStack = savedState?.tabsStack ?: Stack()

        if (tabsStack.isNotEmpty()) {
            tabsStack.forEach {
                fragmentsStack.add(it)
            }
        } else {
            fragmentsStack.add(MainFeature.TabType.Notes)
        }

        viewModelScope.launch {

            outerTabNavigator.tabToOpenFlow
                .collectWhenViewActive()
                .collect { newTab ->
                    updateState {
                        it.copy(currentTab = newTab)
                    }
                }
        }

    }

    override fun firstState(): MainState {
        return MainState(currentTab = fragmentsStack.peek(), debugVisible = false)
    }

    override fun onBackPressed(): Boolean {
        if (fragmentsStack.size <= 1) {
            return false
        }

        fragmentsStack.pop()
        val tabToOpen = fragmentsStack.peek()

        updateState {
            it.copy(currentTab = tabToOpen)
        }
        return true
    }

    fun onShowFragment(tab: MainFeature.TabType) {
        if (fragmentsStack.peek() == tab) {
            return
        }

        fragmentsStack.remove(tab)
        fragmentsStack.push(tab)
        updateState {
            it.copy(
                currentTab = tab
            )
        }
    }

    fun buildFragment(type: MainFeature.TabType): Fragment {
        return when (type) {
            MainFeature.TabType.Notes -> features.notesFeature.navigationContract.createParams()
                .createFragment()
            MainFeature.TabType.Settings -> features.settingsFeature.navigationContract.createParams()
                .createFragment()
        }.apply {
            this.requestParams = RequestParams.createWithIgnoreResult()
        }
    }

    fun debugClicked() {
//        debugLauncher.launch(NoParams)
    }

    @Parcelize
    private data class SavedState(
        val tabsStack: Stack<MainFeature.TabType>,
    ) : Parcelable

}