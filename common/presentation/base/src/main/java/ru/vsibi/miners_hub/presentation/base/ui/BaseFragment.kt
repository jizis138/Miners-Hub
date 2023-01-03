/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.miners_hub.presentation.base.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.vsibi.miners_hub.mvi.BaseViewEvent
import ru.vsibi.miners_hub.mvi.ViewModelInterface
import ru.vsibi.miners_hub.mvi.messageResult
import ru.vsibi.miners_hub.navigation.BackPressConsumer
import ru.vsibi.miners_hub.navigation.model.requestParams
import ru.vsibi.miners_hub.presentation.base.util.onBackPressed
import ru.vsibi.miners_hub.presentation.base.util.showMessage
import ru.vsibi.miners_hub.util.collectWhenStarted
import ru.vsibi.miners_hub.util.doOnCreate
import ru.vsibi.miners_hub.util.doOnStartStop

abstract class BaseFragment<State : Any, Event : Any> : Fragment, BackPressConsumer {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected abstract val vm: ViewModelInterface<State, BaseViewEvent<Event>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.viewState.collectWhenStarted(this, ::onUpdateState)
        vm.viewEvent.collectWhenStarted(this) { event ->
            when (event) {
                is BaseViewEvent.ScreenEvent -> onRecieveEvent(event.event)
                is BaseViewEvent.ShowMessage -> showMessage(event.message)
                is BaseViewEvent.ShowDialog -> showMessage(event.message)
            }
        }

        lifecycle.doOnStartStop(onStart = vm::onViewActive, onStop = vm::onViewInactive)
        //TODO придумать более подходящее решение
        lifecycle.doOnCreate(vm::onCreate)

        childFragmentManager.setFragmentResultListener(
            requestParams.requestKey,
            this
        ) { _, bundle ->
            vm.onMessageResult(bundle.messageResult)
        }
    }

    override fun onBackPressed(): Boolean =
        childFragmentManager.onBackPressed() || vm.onBackPressed()

    protected abstract fun onUpdateState(state: State)
    protected abstract fun onRecieveEvent(event: Event)
}