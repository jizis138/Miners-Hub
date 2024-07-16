/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.util.CallResult

interface ViaRepository {

    suspend fun fetchIncomePerDay(
        coin: String,
        coinPrice: String,
        difficulty: String,
        hashrate: String
    ) : Double

}