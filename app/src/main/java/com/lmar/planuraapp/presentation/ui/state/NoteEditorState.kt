package com.lmar.planuraapp.presentation.ui.state

import com.lmar.planuraapp.domain.enums.NoteColorEnum

data class NoteEditorState (
    val noteId: String = "0",
    val noteTitle: String = "",
    val noteContent: String = "",
    val noteColor: NoteColorEnum = NoteColorEnum.DEFAULT,
    val noteLastModified: Long = System.currentTimeMillis(),
    val isColorPickerVisible: Boolean = false,
    val isNoteModified: Boolean = false
)