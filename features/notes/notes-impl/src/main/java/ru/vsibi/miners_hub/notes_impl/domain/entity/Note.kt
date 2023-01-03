/**
 * Created by Dmitry Popov on 02.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl.domain.entity

data class Note(
    val id: Long,
    val title: String,
    val description: String
)