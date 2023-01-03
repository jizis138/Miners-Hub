/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_api

import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

interface KnowledgeFeature {

    val navigationContract : NavigationContractApi<NoParams, NoResult>

}