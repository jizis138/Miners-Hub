package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.miners_hub.navigation.contract.NavigationContract
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

object IncomePropertiesNavigationContract :
    NavigationContract<IncomePropertiesNavigationContract.Params, NoResult>(IncomePropertiesFragment::class) {

    @Parcelize
    data class Params(
        val calculationMode: CalculationMode
    ) : Parcelable

    enum class CalculationMode {
        Normal, Universal
    }
}