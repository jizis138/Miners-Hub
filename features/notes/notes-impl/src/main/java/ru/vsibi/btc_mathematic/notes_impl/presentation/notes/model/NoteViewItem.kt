/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.notes_impl.presentation.notes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteViewItem(
    val text: String
) : Parcelable