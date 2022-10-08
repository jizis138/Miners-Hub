package ru.vsibi.momento.mvi

import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vsibi.momento.navigation.RootRouter
import ru.vsibi.momento.navigation.contract.CreateResultInterface
import ru.vsibi.momento.navigation.contract.NavigationContractApi
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult
import ru.vsibi.momento.navigation.model.RequestParams
import ru.vsibi.momento.navigation.model.UnitResult
import ru.vsibi.momento.util.CallResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.vsibi.momento.util.PrintableText
import ru.vsibi.momento.util.kotlin.Activable
import ru.vsibi.momento.util.kotlin.activableFlow

abstract class BaseViewModel<S : Any, E : Any>(
    protected val router: RootRouter,
    private val requestParams: RequestParams,
    ) : ViewModel(), ViewModelInterface<S, BaseViewEvent<E>> {

    ////////////////////// State //////////////////////////

    private val _viewState by lazy { MutableStateFlow(firstState()) }
    override val viewState get() = _viewState.asStateFlow()

    private val _viewEvent = Channel<BaseViewEvent<E>>(Channel.UNLIMITED)
    override val viewEvent = _viewEvent.receiveAsFlow()

    protected val currentViewState: S get() = viewState.value

    protected abstract fun firstState(): S

    protected fun updateState(update: (S) -> S) {
        _viewState.update(update)
    }

    protected fun sendEvent(event: BaseViewEvent<E>) {
        _viewEvent.trySend(event)
    }

    protected fun sendEvent(event: E) {
        sendEvent(BaseViewEvent.ScreenEvent(event))
    }

    protected interface LoadController {
        fun update()
    }


    //////////////////// Lifecycle ////////////////////////

    /**
     * @return true, if consumed.
     */
    override fun onBackPressed(): Boolean = false

    private val viewActivable = Activable()

    @CallSuper
    override fun onViewActive() {
        viewActivable.onActive()
    }

    @CallSuper
    override fun onViewInactive() {
        viewActivable.onInactive()
    }

    @CallSuper
    override fun onCreate() = Unit

    protected fun <T> Flow<T>.collectWhenViewActive() =
        activableFlow(originalFlow = this, activable = viewActivable, scope = viewModelScope)

    //////////////////// Messages ////////////////////////

    @CallSuper
    override fun onMessageResult(result: MessageResult) {
        if (result.id != Message.ID_NO_MATTER) {
            error("Override this method to handle MessageResult!")
        }
    }

    protected open val errorConverter: ErrorConverter = DefaultErrorConverter

    protected inline fun <T> CallResult<T>.withErrorHandled(
        showErrorType: ShowErrorType = ShowErrorType.Popup,
        onSuccess: (T) -> Unit,
    ): Unit = when (this) {
        is CallResult.Error -> onError(showErrorType, error)
        is CallResult.Success -> onSuccess(this.data)
    }

    protected fun <T> CallResult<T>.successOrSendError(
        showErrorType: ShowErrorType = ShowErrorType.Popup,
    ): T? = when (this) {
        is CallResult.Error -> {
            onError(showErrorType, error)
            null
        }
        is CallResult.Success -> this.data
    }

    protected fun showMessage(message: Message) {
        sendEvent(BaseViewEvent.ShowMessage(message))
    }

    protected fun showPopup(printableText: PrintableText) {
        showMessage(Message.PopUp(messageText = printableText))
    }

    protected fun showAlert(
        messageText: PrintableText,
        id: String = Message.ID_NO_MATTER,
        titleText: PrintableText? = null,
        actionText: PrintableText? = null,
    ) {
        showMessage(
            Message.Alert(
                id = id,
                messageText = messageText,
                titleText = titleText,
                actionText = actionText,
            )
        )
    }

    protected fun showDialog(titleText: PrintableText, messageText: PrintableText) {
        sendEvent(BaseViewEvent.ShowMessage(Message.Dialog(messageText = messageText, titleText = titleText)))
    }

    protected fun onError(showErrorType: ShowErrorType, throwable: Throwable) {
        val errorInfo = errorConverter.convert(throwable)

        when (showErrorType) {
            ShowErrorType.Popup -> showPopup(errorInfo.description)
            ShowErrorType.Alert -> showAlert(
                titleText = errorInfo.title,
                messageText = errorInfo.description,
            )
            ShowErrorType.Dialog -> showDialog(
                titleText = errorInfo.title,
                messageText = errorInfo.description,
            )
        }
    }

    protected fun exitWithResult(createResultInterface: CreateResultInterface) {
        router.exitWithResult(requestParams.resultKey, createResultInterface)
    }

    protected fun exitWithoutResult() {
        router.exit()
    }

    private val screens = mutableListOf<ScreenLauncherImpl<*, *>>()

    init {
        @Suppress("UNCHECKED_CAST")
        router.observeResult(requestParams.requestKey)
            .onEach {
                screens.forEach { s ->
                    it.consumeResult(
                        getResultInterface = s.navigationContractApi.getResult,
                        consume = s.consumeResult as (Parcelable) -> Unit,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    protected fun <P : Parcelable, R : Parcelable> launcher(
        navigationContractApi: NavigationContractApi<P, R>,
        consumeResult: (R) -> Unit,
    ): ScreenLauncher<P> =
        ScreenLauncherImpl(router, requestParams.requestKey, navigationContractApi, consumeResult)
            .also { screens.add(it) }

    @JvmName("registerScreenNoResult")
    protected fun <P : Parcelable> launcher(
        navigationContractApi: NavigationContractApi<P, NoResult>,
    ): ScreenLauncher<P> = launcher(
        navigationContractApi = navigationContractApi,
        consumeResult = {},
    )

    @JvmName("registerScreenUnitResult")
    protected fun <P : Parcelable> launcher(
        navigationContractApi: NavigationContractApi<P, UnitResult>,
    ): ScreenLauncher<P> = launcher(
        navigationContractApi = navigationContractApi,
        consumeResult = {},
    )

    protected fun ScreenLauncher<NoParams>.launch() {
        launch(NoParams)
    }
}