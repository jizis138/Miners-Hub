/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.btc_mathematic.exchange_rate_impl

import ru.vsibi.btc_mathematic.exchange_rate_api.ExchangeRateFeature
import ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.ExchangeRateNavigationContract

class ExchangeRateFeatureImpl : ExchangeRateFeature {

    override val navigationContract = ExchangeRateNavigationContract

}