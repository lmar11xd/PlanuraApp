package com.lmar.planuraapp.presentation.ui.state

import com.lmar.planuraapp.domain.enums.NoteColorEnum

data class NoteEditorState (
    val noteTitle: String = "",
    val noteContent: String = "",
    val noteColor: NoteColorEnum = NoteColorEnum.DEFAULT,
    val noteLastModified: Long = System.currentTimeMillis()
)