/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.notes_impl.presentation.add_new_note

import android.os.Bundle
import android.view.View
import ru.vsibi.momento.notes_impl.R
import ru.vsibi.momento.notes_impl.databinding.FragmentAddNewNoteBinding
import ru.vsibi.momento.presentation.base.ui.BaseFragment
import ru.vsibi.momento.presentation.base.util.fragmentViewBinding
import ru.vsibi.momento.presentation.base.util.noEventsExpected
import ru.vsibi.momento.presentation.base.util.nothingToUpdate
import ru.vsibi.momento.presentation.base.util.viewModel
import ru.vsibi.momento.util.onClick

class AddNewNoteFragment : BaseFragment<AddNewNoteState, AddNewNoteEvent>(R.layout.fragment_add_new_note) {

    private val binding by fragmentViewBinding(FragmentAddNewNoteBinding::bind)

    override val vm : AddNewNoteViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        create.onClick {
            vm.onAddNewNoteClicked(
                title = inputTitle.text.toString().trim(),
                description = inputDescription.text.toString().trim()
            )
        }
        momentoToolbar.setOnButtonClickListener {
            onBackPressed()
        }
    }

    override fun onUpdateState(state: AddNewNoteState) = nothingToUpdate()

    override fun onRecieveEvent(event: AddNewNoteEvent) = noEventsExpected()

}