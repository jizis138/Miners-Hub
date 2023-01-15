/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history

import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.model.HistoryViewItem

sealed interface HistoryEvent {

    class ShowHistoryMenuDialog(
        val historyViewItem: HistoryViewItem
    ) : HistoryEvent


    object ShowAcceptDeleteAllDialog : HistoryEvent

}