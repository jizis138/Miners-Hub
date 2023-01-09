/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.knowledge_api.model.*
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult
import ru.vsibi.btc_mathematic.util.CallResult

interface KnowledgeFeature {

    val navigationContract: NavigationContractApi<NoParams, NoResult>

    val createFarmNavigationContract: NavigationContractApi<IncomePropertiesParams, IncomePropertiesResult>

    val totalCalculationLauncher: NavigationContractApi<TotalCalculationParams, NoResult>

    @Parcelize
    data class TotalCalculationParams(
        val mode: TotalCalculationMode
    ) : Parcelable


    sealed class TotalCalculationMode : Parcelable {
        @Parcelize
        data class WithReadyCalculation(
            val calculationResult: CalculationState.ReadyResult
        ) : TotalCalculationMode(), Parcelable

        @Parcelize
        data class ParamsForCalculation(
            val electricityPrice: Double,
            val currency: String,
            val miners: List<Miner>
        ) : TotalCalculationMode(), Parcelable
    }

    suspend fun getExchangeRateBTCtoRouble(): ExchangeRate?

    suspend fun calculateBTCIncome(
        hashrate: Double,
        power: Double,
        electricityPrice: Price,
        miners : List<Miner>
    ): CallResult<CalculationState.ReadyResult>

    @Parcelize
    data class IncomePropertiesParams(
        val mode: Mode
    ) : Parcelable

    sealed class IncomePropertiesResult : Parcelable {
        @Parcelize
        data class FarmResult(
            val farm: Farm
        ) : IncomePropertiesResult(), Parcelable

        @Parcelize
        object EmptyResult : IncomePropertiesResult(), Parcelable
    }

    sealed class Mode : Parcelable {
        @Parcelize
        object Normal : Mode(), Parcelable

        @Parcelize
        class EditFarm(val farm: Farm) : Mode(), Parcelable

        @Parcelize
        object CreateFarm : Mode(), Parcelable
    }

}