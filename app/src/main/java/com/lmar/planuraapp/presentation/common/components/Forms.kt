package com.lmar.planuraapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.core.ui.theme.Shapes
import com.lmar.planuraapp.core.utils.getTimeFormatted
import com.lmar.planuraapp.core.utils.toFormatted

@Composable
fun NormalTextComponent(
    value: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = value,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(),
        style = style,
        textAlign = textAlign,
        color = textColor
    )
}

@Composable
fun HeadingTextComponent(
    value: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = MaterialTheme.colorScheme.primary,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = value,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(),
        style = style,
        textAlign = textAlign,
        color = textColor
    )
}

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    icon: ImageVector? = null,
    hasBorder: Boolean = true,
    trailingIcon: ImageVector? = null,
    trailingOnClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val iconContent = @Composable {
        if (icon != null) {
            Icon(icon, contentDescription = "IconForm")
        }
    }

    val trailingContent = @Composable {
        IconButton(
            onClick = trailingOnClick ?: {}
        ) {
            if (trailingIcon != null) {
                Icon(trailingIcon, contentDescription = "IconTrailing")
            }
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = style,
        modifier = modifier
            .fillMaxWidth()
            .clip(Shapes.small)
            .padding(horizontal = 4.dp),
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = if (icon != null) iconContent else null,
        trailingIcon = if (trailingIcon != null) trailingContent else null,
        singleLine = true,
        maxLines = 1,
        colors = textFieldColors(hasBorder)
    )
}

@Composable
fun FormPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    hasBorder: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = style,
        modifier = modifier
            .fillMaxWidth()
            .clip(Shapes.small)
            .padding(horizontal = 4.dp),
        label = { Text(label) },
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Password),
        keyboardActions = keyboardActions,
        leadingIcon = { Icon(icon, contentDescription = "IconForm") },
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
        colors = textFieldColors(hasBorder)
    )
}

@Composable
fun FormCheckbox(
    text: String,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    hasBorder: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    var isChecked by remember { mutableStateOf(checked) }

    FormContainer(hasBorder = hasBorder) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onCheckedChange?.invoke(it)
                },
                colors = CheckboxDefaults.colors(
                    disabledCheckedColor = MaterialTheme.colorScheme.outline,
                    disabledUncheckedColor = MaterialTheme.colorScheme.outline,

                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline,

                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )

            Spacer(modifier = Modifier.width(4.dp))

            NormalTextComponent(
                value = text,
                textColor = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun FormContainer(
    title: String? = null,
    value: String? = null,
    placeholder: String = "",
    icon: ImageVector? = null,
    hasBorder: Boolean = true,
    onTapped: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier =
            if (hasBorder) Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(4.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = Shapes.small
                )
            else Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .clickable(
                    enabled = true,
                    onClick = onTapped
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title ?: "",
                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = value ?: placeholder,
                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                textAlign = TextAlign.Right,
                color =
                    if (value == null) MaterialTheme.colorScheme.outline
                    else MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "IconDropDown",
                    modifier = Modifier.padding(bottom = 4.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }

        content()
    }
}

@Composable
fun DividerTextComponent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
        Text(
            "o",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDatePickerDialog(
    title: String? = null,
    initialDateMillis: Long? = null,
    hasBorder: Boolean = true,
    onDateSelected: (Long) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    FormContainer(
        title = title,
        value = initialDateMillis?.toFormatted() ?: "",
        icon = Icons.Default.CalendarMonth,
        hasBorder = hasBorder,
        onTapped = { showDialog = true }
    ) {
        if (showDialog) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = initialDateMillis
            )

            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                        showDialog = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                    }) {
                        Text("Cancelar")
                    }
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    DatePicker(
                        title = {
                            Column(
                                modifier = Modifier
                                    .padding(start = 24.dp, top = 16.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Text("Selecciona una fecha")
                            }
                        },
                        state = datePickerState
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTimePickerDialog(
    title: String? = null,
    initialHour: Int = 12,
    initialMinute: Int = 0,
    hasBorder: Boolean = true,
    onTimeSelected: (hour: Int, minute: Int) -> Unit = { _, _ -> }
) {
    var showDialog by remember { mutableStateOf(false) }

    FormContainer(
        title = title,
        value = getTimeFormatted(
            initialHour,
            initialMinute
        ),
        icon = Icons.Default.Timer,
        hasBorder = hasBorder,
        onTapped = { showDialog = true }
    ) {
        if (showDialog) {
            val timePickerState = rememberTimePickerState(
                initialHour = initialHour,
                initialMinute = initialMinute
            )

            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        onTimeSelected(timePickerState.hour, timePickerState.minute)
                        showDialog = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        TimePicker(state = timePickerState)
                    }
                }
            )
        }
    }
}

@Composable
private fun textFieldColors(hasBorder: Boolean = true) = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = if (hasBorder) MaterialTheme.colorScheme.outline else Color.Transparent,
    unfocusedLabelColor = MaterialTheme.colorScheme.outline,
    unfocusedTextColor = MaterialTheme.colorScheme.outline,
    unfocusedLeadingIconColor = MaterialTheme.colorScheme.outline,
    unfocusedTrailingIconColor = MaterialTheme.colorScheme.outline,
    unfocusedContainerColor = Color.Transparent,

    disabledBorderColor = if (hasBorder) MaterialTheme.colorScheme.outline else Color.Transparent,
    disabledLabelColor = MaterialTheme.colorScheme.outline,
    disabledTextColor = MaterialTheme.colorScheme.outline,
    disabledLeadingIconColor = MaterialTheme.colorScheme.outline,
    disabledTrailingIconColor = MaterialTheme.colorScheme.outline,
    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,

    focusedBorderColor = if (hasBorder) MaterialTheme.colorScheme.primary else Color.Transparent,
    focusedLabelColor = MaterialTheme.colorScheme.primary,
    focusedTextColor = MaterialTheme.colorScheme.primary,
    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
    focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
    focusedContainerColor = Color.Transparent,

    cursorColor = MaterialTheme.colorScheme.primary
)

@Preview(showBackground = true)
@Composable
private fun FormsPreview() {
    PlanuraAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormTextField(
                "Data",
                onValueChange = {},
                label = "Label",
                icon = Icons.Default.Visibility
            )
            FormPasswordTextField(
                "Data",
                onValueChange = {},
                label = "Label",
                icon = Icons.Default.Visibility
            )
            FormCheckbox("Data", checked = true, onCheckedChange = {})
            DividerTextComponent()
            FormDatePickerDialog(
                "Fecha",
                initialDateMillis = System.currentTimeMillis()
            )
            FormTimePickerDialog(title = "Hora", initialHour = 16, initialMinute = 45)
        }
    }
}