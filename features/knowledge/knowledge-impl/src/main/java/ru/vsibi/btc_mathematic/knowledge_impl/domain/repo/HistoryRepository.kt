/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.domain.repo

import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.util.CallResult

interface HistoryRepository {

    suspend fun saveCalculation(readyResult : CalculationState.ReadyResult)

    suspend fun getSavedCalculations() : CallResult<List<CalculationState.ReadyResult>>

    suspend fun removeItem(id: Long)

    suspend fun removeAllItems()

}