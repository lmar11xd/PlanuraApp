package com.lmar.planuraapp.presentation.ui.event

import com.lmar.planuraapp.domain.enums.NoteColorEnum
import com.lmar.planuraapp.presentation.common.components.SnackbarType

sealed class NoteEditorEvent {
    data class ShowMessage(val message: String, val type: SnackbarType): NoteEditorEvent()

    data class SetTitle(val title: String): NoteEditorEvent()
    data class SetContent(val content: String): NoteEditorEvent()
    data class SetColor(val color: NoteColorEnum): NoteEditorEvent()

    object ShowColorPicker: NoteEditorEvent()
    object DeleteNote: NoteEditorEvent()
    object SaveNote: NoteEditorEvent()

    object ToBack: NoteEditorEvent()
}