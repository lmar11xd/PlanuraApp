package com.lmar.planuraapp.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.lmar.planuraapp.domain.model.Task

@Composable
fun TaskEditorForm(
    show: Boolean,
    task: Task,
    onDismiss: () -> Unit = {},
    onSave: (String) -> Unit = {}
) {
    if (!show) return

    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (task.id == "0") "Nueva tarea" else "Editar tarea")
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PlanuraTextField(
                    fontSize = 14,
                    fontWeight = FontWeight.Normal,
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "Descripción de la tarea"
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(description) }
            ) {
                Text("Guardar")
            }
        }
    )
}