package com.lmar.planuraapp.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.domain.model.Task
import kotlinx.coroutines.delay

@Composable
fun TaskEditorForm(
    show: Boolean,
    task: Task,
    onDismiss: () -> Unit = {},
    onSave: (String) -> Unit = {}
) {
    if (!show) return

    val focusRequester = remember { FocusRequester() }

    var description by remember { mutableStateOf(task.description) }

    LaunchedEffect(Unit) {
        delay(100) // Esperar a que se muestre el diálogo
        focusRequester.requestFocus()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (task.id == "0") "Nueva tarea" else "Editar tarea", color = MaterialTheme.colorScheme.primary)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PlanuraTextArea(
                    fontSize = 14,
                    fontWeight = FontWeight.Normal,
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "Descripción de la tarea",
                    modifier = Modifier.focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(description) },
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Guardar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskEditorFormPreview() {
    PlanuraAppTheme {
        TaskEditorForm(
            show = true,
            task = Task("0", "Instale la versión más reciente de PowerShell para obtener nuevas características y mejoras. https://aka.ms/PSWindows", false, 0, 0),
            onDismiss = {},
            onSave = {}
        )
    }
}