package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.presentation.common.components.Loading
import com.lmar.planuraapp.presentation.navigation.handleUiEvents
import com.lmar.planuraapp.presentation.ui.component.NoteCard
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
        onFABClick = { onEvent(NoteEvent.ToEditor("0")) }
    ) {
        if (noteState.notes.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    Icons.Default.Description,
                    contentDescription = "Sin Notas",
                    tint = Color.Gray,
                )

                Text(
                    "No hay notas",
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2), // 2 columnas
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalItemSpacing = 0.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(noteState.notes) { note ->
                    NoteCard(
                        note = note,
                        onTap = { onEvent(NoteEvent.ToEditor(note.id)) }
                    )
                }
            }
        }

        if (noteState.isLoading) {
            Loading()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotesScreenPreview() {
    val notes = listOf(
        Note("1", "Nota 1", "Contenido de la nota 1", "BLUE", 0, 0),
        Note("2", "Nota 2", "Contenido de la nota 2", "RED", 0, 0),
        Note("3", "Nota 3", "Contenido de la nota 3", "DEFAULT", 0, 0),
        Note("4", "Nota 4", "Contenido de la nota 4", "DEFAULT", 0, 0),
        Note("5", "Nota 5", "Contenido de la nota 5", "YELLOW", 0, 0),
        Note("6", "Nota 6", "Contenido de la nota 6", "GREEN", 0, 0),
    )

    PlanuraAppTheme {
        NotesScreen(
            noteState = NoteState(notes = notes)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NotesScreenEmptyPreview() {
    PlanuraAppTheme {
        NotesScreen(
            noteState = NoteState(notes = emptyList())
        )
    }
}