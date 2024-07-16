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

        abstract val usingViaBtc : Boolean

        @Parcelize
        data class WithReadyCalculation(
            override val usingViaBtc: Boolean,
            val calculationResult: CalculationState.ReadyResult
        ) : TotalCalculationMode(), Parcelable

        @Parcelize
        data class ParamsForCalculation(
            override val usingViaBtc: Boolean,
            val electricityPrice: Double,
            val currency: String,
            val miners: List<Miner>,
            val exchangeRate: ExchangeRate?
        ) : TotalCalculationMode(), Parcelable
    }

    suspend fun getExchangeRateBTCtoCurrency(vararg currency : String): List<ExchangeRate>?

    suspend fun calculateBTCIncome(
        usingViaBtc : Boolean,
        hashrate: Double,
        power: Double,
        electricityPrice: Price,
        miners : List<Miner>,
        needSaveToHistory : Boolean
    ): CallResult<CalculationState.ReadyResult>

    @Parcelize
    data class IncomePropertiesParams(
        val mode: Mode
    ) : Parcelable

    sealed class IncomePropertiesResult : Parcelable {
        @Parcelize
        data class FarmEditResult(
            val farm: Farm
        ) : IncomePropertiesResult(), Parcelable

        @Parcelize
        data class FarmCreateResult(
            val farm: Farm
        ) : IncomePropertiesResult(), Parcelable

        @Parcelize
        object EmptyResult : IncomePropertiesResult(), Parcelable
    }

    sealed class Mode : Parcelable {

        abstract val usingViaBtc : Boolean

        @Parcelize
        class Normal(override val usingViaBtc: Boolean) : Mode(), Parcelable

        @Parcelize
        class EditFarm(override val usingViaBtc: Boolean, val farm: Farm) : Mode(), Parcelable

        @Parcelize
        class CreateFarm(override val usingViaBtc: Boolean) : Mode(), Parcelable
    }

}