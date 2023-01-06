/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.miners_hub.main_impl.presentation.main

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import ru.vsibi.miners_hub.core.environment.Environment
import ru.vsibi.miners_hub.exchange_rate_api.ExchangeRateFeature
import ru.vsibi.miners_hub.knowledge_api.KnowledgeFeature
import ru.vsibi.miners_hub.main_api.MainFeature
import ru.vsibi.miners_hub.main_impl.domain.logic.OuterTabNavigator
import ru.vsibi.miners_hub.mining_api.MiningFeature
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.mvi.provideSavedState
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.navigation.model.createParams
import ru.vsibi.miners_hub.navigation.model.requestParams
import ru.vsibi.miners_hub.notes_api.NotesFeature
import ru.vsibi.miners_hub.settings_api.SettingsFeature
import java.util.*

class MainViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    savedStateHandle: SavedStateHandle,
    private val outerTabNavigator: OuterTabNavigator,
    private val environment: Environment,
    private val features: Features
) : BaseViewModel<MainState, MainEvent>(router, requestParams) {

    class Features(
        val notesFeature: NotesFeature,
        val settingsFeature: SettingsFeature,
        val knowledgeFeature: KnowledgeFeature,
        val exchangeRateFeature: ExchangeRateFeature,
        val miningFeature: MiningFeature,
    )

    private val fragmentsStack = Stack<MainFeature.TabType>()

//    private val debugLauncher = registerScreen(features.debugFeature.navigationContract)

    init {
        val savedState =
            savedStateHandle.provideSavedState { SavedState(tabsStack = fragmentsStack) }
        val tabsStack = savedState?.tabsStack ?: Stack()

        if (tabsStack.isNotEmpty()) {
            tabsStack.forEach {
                fragmentsStack.add(it)
            }
        } else {
            fragmentsStack.add(MainFeature.TabType.Knowledge)
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
            MainFeature.TabType.Settings -> features.settingsFeature.navigationContract.createParams()
                .createFragment()
            MainFeature.TabType.Knowledge -> features.knowledgeFeature.navigationContract.createParams()
                .createFragment()
            MainFeature.TabType.Rates -> features.exchangeRateFeature.navigationContract.createParams()
                .createFragment()
            MainFeature.TabType.Mining -> features.miningFeature.navigationContract.createParams()
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