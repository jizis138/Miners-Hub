/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.miners_hub.exchange_rate_impl

import ru.vsibi.miners_hub.exchange_rate_api.ExchangeRateFeature
import ru.vsibi.miners_hub.exchange_rate_impl.presentation.ExchangeRateNavigationContract
import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

class ExchangeRateFeatureImpl : ExchangeRateFeature {

    override val navigationContract = ExchangeRateNavigationContract

}