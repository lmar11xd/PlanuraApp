package com.lmar.planuraapp.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme

enum class DialogType(
    val color: Color
) {
    CONFIRM(Color(0xFF4CAF50)),
    DELETE(Color(0xFFF44336)),
    DEFAULT(Color(0xFF2196F3)),
}

@Composable
fun Dialog(
    title: String,
    message: String,
    type: DialogType = DialogType.DEFAULT,
    showDialog: Boolean = false,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            AnimatedVisibility(
                visible = showDialog,
                enter = scaleIn(initialScale = 0.8f) + fadeIn(),
                exit = scaleOut(targetScale = 0.8f) + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            color = if (type == DialogType.DELETE) type.color else MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (type == DialogType.DEFAULT) {
                            Button(
                                onClick = onConfirm,
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("OK", color = Color.White, fontSize = 12.sp)
                            }
                        } else {
                            OutlinedButton(
                                onClick = onDismiss,
                                shape = MaterialTheme.shapes.medium,
                                border = null,
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor =
                                        if (type == DialogType.DELETE) type.color
                                        else MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Cancelar", fontSize = 12.sp)
                            }

                            Spacer(modifier = Modifier.padding(4.dp))

                            Button(
                                onClick = onConfirm,
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if (type == DialogType.DELETE) Color.Red
                                        else MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    if (type == DialogType.DELETE) "Eliminar" else "Aceptar",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    PlanuraAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Dialog(
                title = "Título del diálogo",
                message = "Mensaje del diálogo",
                type = DialogType.DEFAULT,
                showDialog = true,
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogConfirmPreview() {
    PlanuraAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Dialog(
                title = "Título del diálogo",
                message = "Mensaje del diálogo",
                type = DialogType.CONFIRM,
                showDialog = true,
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogDeletePreview() {
    PlanuraAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Dialog(
                title = "Título del diálogo",
                message = "Mensaje del diálogo",
                type = DialogType.DELETE,
                showDialog = true,
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}