package ru.vsibi.momento.util

import android.view.View
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding

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
    val firstErrorState: LoadingState.Error<E>? = filterIsInstance<LoadingState.Error<E>>().firstOrNull()

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
    renderData: (T) -> Unit = {},
) {
    progressContainer?.isVisible = loadingState.isLoading
    errorContainer?.isVisible = loadingState is LoadingState.Error
    contentContainer?.isVisible = loadingState.isSuccess

    if (loadingState is LoadingState.Error) {
        //todo добавить стандартное отображение ошибки
//        errorContainer.renderError(loadingState.error)
    }

    loadingState.dataOrNull?.apply(renderData)
}