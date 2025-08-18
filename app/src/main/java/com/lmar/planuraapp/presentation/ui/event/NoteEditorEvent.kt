package com.lmar.planuraapp.presentation.ui.event

import com.lmar.planuraapp.domain.enums.PlanuraColorEnum
import com.lmar.planuraapp.presentation.common.components.SnackbarType

sealed class NoteEditorEvent {
    data class ShowMessage(val message: String, val type: SnackbarType): NoteEditorEvent()

    data class SetTitle(val title: String): NoteEditorEvent()
    data class SetContent(val content: String): NoteEditorEvent()
    data class SetColor(val color: PlanuraColorEnum): NoteEditorEvent()

    object ShowColorPicker: NoteEditorEvent()
    object DeleteNote: NoteEditorEvent()
    object SaveNote: NoteEditorEvent()

    object HideNoteDeletedDialog: NoteEditorEvent()
    object ConfirmDeleteNote: NoteEditorEvent()

    object ToBack: NoteEditorEvent()
}