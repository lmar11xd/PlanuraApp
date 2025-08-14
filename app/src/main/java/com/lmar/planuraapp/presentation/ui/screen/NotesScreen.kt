package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.domain.enums.NoteColorEnum
import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.presentation.common.components.Loading
import com.lmar.planuraapp.presentation.navigation.handleUiEvents
import com.lmar.planuraapp.presentation.ui.component.ScreenScaffold
import com.lmar.planuraapp.presentation.ui.event.NoteEvent
import com.lmar.planuraapp.presentation.ui.state.NoteState
import com.lmar.planuraapp.presentation.viewmodel.NoteViewModel

@Composable
fun NotesScreenContainer(
    navController: NavHostController,
    noteViewModel: NoteViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val noteState by noteViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        navController.handleUiEvents(
            scope = coroutineScope,
            uiEventFlow = noteViewModel.eventFlow,
        )
    }

    NotesScreen(
        noteState = noteState,
        onEvent = noteViewModel::onEvent
    )
}

@Composable
private fun NotesScreen(
    noteState: NoteState = NoteState(),
    onEvent: (NoteEvent) -> Unit = {}
) {
    ScreenScaffold(
        title = "Notas",
        withFAB = true,
        actions = {
            IconButton(
                onClick = { },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        onFABClick = { onEvent(NoteEvent.ToEditor("0")) }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // 2 columnas
            modifier = Modifier.padding(8.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(noteState.notes) { note ->
                NoteCard(
                    note = note,
                    onTap = { onEvent(NoteEvent.ToEditor(note.id)) }
                )
            }
        }

        if (noteState.isLoading) {
            Loading()
        }
    }
}

@Composable
fun NoteCard(note: Note, onTap: () -> Unit = {}) {
    val noteColor = NoteColorEnum.valueOf(note.color)

    Surface(
        color = Color(noteColor.container), // Amarillo claro
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        modifier = Modifier
            /*.border(
                width = 1.dp,
                color = Color(noteColor.base),
                shape = RoundedCornerShape(8.dp)
            )*/
            .fillMaxWidth()
            .clickable(onClick = onTap)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = note.title ?: "Sin título",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = note.content ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}