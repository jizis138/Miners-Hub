/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl

import kotlinx.coroutines.flow.*
import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.HistoryStorage
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.HistoryRepository
import ru.vsibi.btc_mathematic.util.CallResult
import ru.vsibi.btc_mathematic.util.callForResult
import ru.vsibi.btc_mathematic.util.onSuccess

class HistoryRepoImpl(private val historyStorage: HistoryStorage) : HistoryRepository {

    override suspend fun saveCalculation(readyResult: CalculationState.ReadyResult) {
        historyStorage.saveHistory(readyResult)
    }

    override suspend fun getSavedCalculations(): CallResult<List<CalculationState.ReadyResult>> = callForResult {
        historyStorage.getHistoryItems()
    }

    override suspend fun removeItem(id: Long) = historyStorage.removeItem(id)

    override suspend fun removeAllItems() = historyStorage.removeAllItems()

}