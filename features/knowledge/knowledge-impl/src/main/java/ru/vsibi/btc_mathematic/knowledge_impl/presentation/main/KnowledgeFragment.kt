/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.main

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.FragmentKnowledgeBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.main.adapter.KnowledgeAdapter
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.uikit.SpacingItemDecoration
import ru.vsibi.btc_mathematic.util.dp

class KnowledgeFragment : BaseFragment<KnowledgeState, KnowledgeEvent>(R.layout.fragment_knowledge) {

    override val vm : KnowledgeViewModel by viewModel()

    private val binding by fragmentViewBinding (FragmentKnowledgeBinding::bind)

    private val adapter = KnowledgeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter
        list.addItemDecoration(SpacingItemDecoration { index, itemCount ->
            val verticalMargin = if (index == 0) {
                8.dp
            } else {
                12.dp
            }

            Rect(
                20.dp,
                verticalMargin,
                20.dp,
                12.dp,
            )
        })
    }

    override fun onUpdateState(state: KnowledgeState) {
        adapter.items = state.items
    }

    override fun onRecieveEvent(event: KnowledgeEvent) {}


}