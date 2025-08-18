package com.lmar.planuraapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.core.ui.theme.Shapes

@Composable
fun NormalTextComponent(
    value: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    textColor: Color = MaterialTheme.colorScheme.tertiary,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = value,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = textAlign
        ),
        color = textColor
    )
}

@Composable
fun HeadingTextComponent(
    value: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
    textColor: Color = MaterialTheme.colorScheme.primary,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = value,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            textAlign = textAlign
        ),
        color = textColor
    )
}

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(Shapes.small)
            .padding(horizontal = 4.dp),
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = { Icon(icon, contentDescription = "IconForm") },
        singleLine = true,
        maxLines = 1,
        colors = textFieldColors()
    )
}

@Composable
fun FormPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
        colors = textFieldColors()
    )
}

@Composable
fun FormCheckbox(
    text: String,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    var isChecked by remember { mutableStateOf(checked) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
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

                checkedColor = MaterialTheme.colorScheme.onPrimary,
                uncheckedColor = MaterialTheme.colorScheme.outline,

                checkmarkColor = MaterialTheme.colorScheme.primary
            )
        )
        NormalTextComponent(
            value = text,
            fontSize = 16.sp,
            textColor = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
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

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    unfocusedLabelColor = MaterialTheme.colorScheme.outline,
    unfocusedTextColor = MaterialTheme.colorScheme.outline,
    unfocusedLeadingIconColor = MaterialTheme.colorScheme.outline,
    unfocusedTrailingIconColor = MaterialTheme.colorScheme.outline,
    unfocusedContainerColor = Color.Transparent,

    disabledBorderColor = MaterialTheme.colorScheme.outline,
    disabledLabelColor = MaterialTheme.colorScheme.outline,
    disabledTextColor = MaterialTheme.colorScheme.outline,
    disabledLeadingIconColor = MaterialTheme.colorScheme.outline,
    disabledTrailingIconColor = MaterialTheme.colorScheme.outline,
    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,

    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
    focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
    focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
    focusedContainerColor = Color.Transparent,

    cursorColor = MaterialTheme.colorScheme.onPrimary
)

@Preview(showBackground = true)
@Composable
private fun FormsPreview() {
    PlanuraAppTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(color = Color.Black).padding(16.dp),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormTextField("Data", {}, "Label", Icons.Default.Visibility)
            FormPasswordTextField("Data", {}, "Label", Icons.Default.Visibility)
            FormCheckbox("Data", checked = true, onCheckedChange = {})
        }
    }
}