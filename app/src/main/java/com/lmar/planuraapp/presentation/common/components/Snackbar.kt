package com.lmar.planuraapp.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

enum class SnackbarType(
    val color: Color,
    val icon: ImageVector
) {
    SUCCESS(Color(0xFF4CAF50), Icons.Default.CheckCircle),
    ERROR(Color(0xFFF44336), Icons.Default.Error),
    INFO(Color(0xFF2196F3), Icons.Default.Info),
    WARN(Color(0xFFFFC107), Icons.Default.Warning)
}

data class SnackbarEvent(
    val message: String,
    val type: SnackbarType = SnackbarType.INFO
)

object SnackbarManager {
    private val snackbarChannel = Channel<SnackbarEvent>()
    val snackbarFlow = snackbarChannel.receiveAsFlow()

    suspend fun showMessage(message: String, type: SnackbarType = SnackbarType.INFO) {
        snackbarChannel.send(SnackbarEvent(message, type))
    }
}

@Composable
fun Snackbar(snackbarData: SnackbarData, type: SnackbarType) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = type.color,
        shadowElevation = 6.dp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = type.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = snackbarData.visuals.message,
                color = Color.White
            )
        }
    }
}