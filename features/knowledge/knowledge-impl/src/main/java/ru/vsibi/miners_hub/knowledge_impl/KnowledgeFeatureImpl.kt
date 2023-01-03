/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl

import ru.vsibi.miners_hub.knowledge_api.KnowledgeFeature
import ru.vsibi.miners_hub.knowledge_impl.presentation.main.KnowledgeNavigationContract

class KnowledgeFeatureImpl : KnowledgeFeature {

    override val navigationContract = KnowledgeNavigationContract

}