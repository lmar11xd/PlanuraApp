package com.lmar.planuraapp.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lmar.planuraapp.core.utils.formatTimestamp
import com.lmar.planuraapp.domain.enums.PlanuraColorEnum
import com.lmar.planuraapp.domain.model.Note


@Composable
fun NoteCard(
    note: Note,
    textColor: Color = Color.DarkGray,
    onTap: () -> Unit = {}
) {
    val noteColor = PlanuraColorEnum.valueOf(note.color)

    Surface(
        color = Color(noteColor.container), // Amarillo claro
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onTap)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = note.title ?: "Sin título",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = textColor
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = note.content ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = formatTimestamp(note.updatedAt),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.End,
                color = textColor
            )
        }
    }
}