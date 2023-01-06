/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.vsibi.miners_hub.knowledge_api.model.CalculationState
import ru.vsibi.miners_hub.knowledge_api.KnowledgeFeature
import ru.vsibi.miners_hub.knowledge_api.model.Miner
import ru.vsibi.miners_hub.knowledge_api.model.Price
import ru.vsibi.miners_hub.knowledge_impl.domain.logic.CalculationInteractor
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesNavigationContract
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.TotalNavigationContract
import ru.vsibi.miners_hub.knowledge_impl.presentation.main.KnowledgeNavigationContract
import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult
import ru.vsibi.miners_hub.util.CallResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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