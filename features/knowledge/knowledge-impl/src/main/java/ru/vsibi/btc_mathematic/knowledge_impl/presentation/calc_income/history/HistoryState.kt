/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history

import ru.vsibi.btc_mathematic.util.ErrorInfo
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.model.HistoryViewItem
import ru.vsibi.btc_mathematic.uikit.LoadingState

data class HistoryState(
    val loadingState : LoadingState<ErrorInfo, List<HistoryViewItem>>
)