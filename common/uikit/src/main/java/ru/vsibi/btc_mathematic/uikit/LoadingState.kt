package ru.vsibi.btc_mathematic.uikit

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import ru.vsibi.btc_mathematic.uikit.databinding.IncludeEmptyViewBinding
import ru.vsibi.btc_mathematic.util.ErrorInfo
import ru.vsibi.btc_mathematic.util.onClick
import ru.vsibi.btc_mathematic.util.setPrintableTextOrGone

sealed class LoadingState<out E, out T> {
    object None : LoadingState<Nothing, Nothing>()
    object Loading : LoadingState<Nothing, Nothing>()
    data class Error<out E>(val error: E) : LoadingState<E, Nothing>()
    data class Success<out T>(val data: T) : LoadingState<Nothing, T>()

    companion object
}

fun LoadingState.Companion.Error() = LoadingState.Error(Unit)
fun LoadingState.Companion.Success() = LoadingState.Success(Unit)

fun LoadingState.Companion.fromLoading(isLoading: Boolean): LoadingState<Nothing, Unit> =
    if (isLoading) LoadingState.Loading else LoadingState.Success()

val LoadingState<*, *>.isLoading get() = this == LoadingState.Loading
val LoadingState<*, *>.isError get() = this is LoadingState.Error
val LoadingState<*, *>.isSuccess get() = this is LoadingState.Success

val <T> LoadingState<*, T>.dataOrNull get(): T? = (this as? LoadingState.Success)?.data

fun List<LoadingState<*, *>>.commonState(): LoadingState<Unit, Unit> = when {
    any { it is LoadingState.Error } -> LoadingState.Error()
    all { it is LoadingState.Success } -> LoadingState.Success()
    any { it is LoadingState.Loading } -> LoadingState.Loading
    else -> LoadingState.None
}

fun <E> List<LoadingState<E, *>>.commonStateWithError(): LoadingState<E, Unit> {
    val firstErrorState: LoadingState.Error<E>? =
        filterIsInstance<LoadingState.Error<E>>().firstOrNull()

    return when {
        firstErrorState != null -> LoadingState.Error(firstErrorState.error)
        all { it is LoadingState.Success } -> LoadingState.Success()
        any { it is LoadingState.Loading } -> LoadingState.Loading
        else -> LoadingState.None
    }
}

inline fun <T> renderLoadingState(
    loadingState: LoadingState<ErrorInfo, T>,
    progressContainer: View?,
    errorContainer: View?,
    contentContainer: View?,
    emptyContainer: IncludeEmptyViewBinding?,
    renderData: (T) -> Unit = {},
    noinline onRetryClicked: () -> Unit,
) {
    progressContainer?.isVisible = loadingState.isLoading
    errorContainer?.isVisible = loadingState is LoadingState.Error
    contentContainer?.isVisible = loadingState.isSuccess
    if (loadingState is LoadingState.Error) {
        errorContainer.renderError(loadingState.error, onRetryClicked)
    }

    loadingState.dataOrNull?.apply(renderData)

    val isEmpty = (loadingState.dataOrNull as? List<*>)?.isEmpty() ?: false
    emptyContainer?.emptyView?.isVisible = isEmpty
}

fun View?.renderError(error: ErrorInfo, onRetryClicked: () -> Unit) {
    this?.findViewById<TextView>(R.id.error_title)?.setPrintableTextOrGone(error.title)
    this?.findViewById<TextView>(R.id.error_description)?.setPrintableTextOrGone(error.description)
    this?.findViewById<Button>(R.id.retry)?.let {
        it.onClick {
            onRetryClicked()
        }
    }
}

