/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.domain.logic.CalculationInteractor
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesNavigationContract
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.TotalNavigationContract
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.main.KnowledgeNavigationContract
import ru.vsibi.btc_mathematic.util.CallResult

class KnowledgeFeatureImpl(
    private val calculationInteractor: CalculationInteractor
) : KnowledgeFeature {

    override val navigationContract = KnowledgeNavigationContract

    override val createFarmNavigationContract = IncomePropertiesNavigationContract

    override val totalCalculationLauncher = TotalNavigationContract

    override suspend fun getExchangeRateBTCtoRouble() = calculationInteractor.fetchBTCtoRoubleRate()

    override suspend fun calculateBTCIncome(
        hashrate: Double,
        power: Double,
        electricityPrice: Price,
        miners: List<Miner>
    ): CallResult<CalculationState.ReadyResult> = withTimeoutOrNull(5000) {
        suspendCancellableCoroutine<CallResult<CalculationState.ReadyResult>> { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                calculationInteractor.calculateBTCIncome(
                    hashrate,
                    power,
                    electricityPrice,
                    miners,
                    false
                ).onEach {
                    when (it) {
                        is CalculationState.Error -> {
                            continuation.resumeWith(Result.failure(Throwable()))
                        }
                        is CalculationState.ReadyResult -> {
                            continuation.resumeWith(Result.success(CallResult.Success(it)))
                        }
                    }
                }.launchIn(this)
            }
        }
    } ?: CallResult.Error(Throwable())
}