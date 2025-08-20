package com.lmar.planuraapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme

data class DropdownOption(
    val label: String,
    val value: String
)

@Composable
fun SingleDropdown(
    title: String? = null,
    options: List<DropdownOption> = emptyList(),
    selectedOption: DropdownOption?,
    hasBorder: Boolean = true,
    onOptionSelected: (DropdownOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    FormContainer(
        title = title,
        value = selectedOption?.label,
        icon = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown,
        hasBorder = hasBorder,
        onTapped = { expanded = !expanded }
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SingleDropDownPreview() {
    val options = listOf(
        DropdownOption("Opción 1", "1"),
        DropdownOption("Opción 2", "2"),
        DropdownOption("Opción 3", "3")
    )

    PlanuraAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SingleDropdown("Sin Opciones", emptyList(), null) {}
            SingleDropdown("Opciones", options, options[0]) {}
            SingleDropdown("Sin Border", options, options[0], hasBorder = false) {}
        }
    }
}