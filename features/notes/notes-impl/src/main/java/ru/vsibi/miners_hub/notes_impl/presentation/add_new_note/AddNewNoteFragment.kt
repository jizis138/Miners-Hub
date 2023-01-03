/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl.presentation.add_new_note

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.vsibi.miners_hub.notes_impl.R
import ru.vsibi.miners_hub.notes_impl.databinding.FragmentAddNewNoteBinding
import ru.vsibi.miners_hub.notes_impl.presentation.add_new_note.adapter.TextStylesAdapter
import ru.vsibi.miners_hub.notes_impl.presentation.add_new_note.model.TextStyleViewItem
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.noEventsExpected
import ru.vsibi.miners_hub.presentation.base.util.nothingToUpdate
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.util.onClick

class AddNewNoteFragment : BaseFragment<AddNewNoteState, AddNewNoteEvent>(R.layout.fragment_add_new_note) {

    private val binding by fragmentViewBinding(FragmentAddNewNoteBinding::bind)

    override val vm : AddNewNoteViewModel by viewModel()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private val textStylesAdapter = TextStylesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        create.onClick {
            vm.onAddNewNoteClicked(
                description = inputDescription.text.toString().trim()
            )
        }
        momentoToolbar.setOnButtonClickListener {
            onBackPressed()
        }

        textStyle.onClick {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        bottomSheetStyles.textStyles.adapter = textStylesAdapter
        textStylesAdapter.items = listOf(
            TextStyleViewItem(R.drawable.ic_b),
            TextStyleViewItem(R.drawable.ic_i),
            TextStyleViewItem(R.drawable.ic_u),
            TextStyleViewItem(R.drawable.ic_s),
        )
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetStyles.root)
        bottomSheetBehavior.isHideable = true
    }

    override fun onUpdateState(state: AddNewNoteState) = nothingToUpdate()

    override fun onRecieveEvent(event: AddNewNoteEvent) = noEventsExpected()

}