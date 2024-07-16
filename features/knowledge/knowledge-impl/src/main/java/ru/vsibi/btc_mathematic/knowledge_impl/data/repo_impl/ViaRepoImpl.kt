/**
 * Created by Dmitry Popov on 21.04.2024.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl

import ru.vsibi.btc_mathematic.knowledge_impl.data.service.ViaBtcService
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.ViaRepository

class ViaRepoImpl(private val viaBtcService: ViaBtcService) : ViaRepository {

    override suspend fun fetchIncomePerDay(
        coin: String,
        coinPrice: String,
        difficulty: String,
        hashrate: String
    ) = viaBtcService.fetchIncomePerDay(
        coin, coinPrice, difficulty, hashrate
    )

}