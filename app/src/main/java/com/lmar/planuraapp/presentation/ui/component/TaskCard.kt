package com.lmar.planuraapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lmar.planuraapp.domain.enums.PlanuraColorEnum
import com.lmar.planuraapp.domain.model.Task


@Composable
fun TaskCard(
    task: Task,
    onSelected: () -> Unit = {},
    onDeleted: (Task) -> Unit = {},
    onCompletedChange: (String, Boolean) -> Unit
) {
    val color = if (task.isCompleted) {
        Color.LightGray
    } else {
        Color.DarkGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(PlanuraColorEnum.DEFAULT.container),
                shape = MaterialTheme.shapes.small
            )
            .clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onCompletedChange(task.id, it) },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            task.description,
            color = color,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = { onDeleted(task) }
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = color
            )
        }
    }
}
