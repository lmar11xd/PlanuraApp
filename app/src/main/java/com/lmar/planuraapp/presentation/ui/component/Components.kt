package com.lmar.planuraapp.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme

@Composable
fun PlanuraTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fontSize: Int = 16,
    fontWeight: FontWeight = FontWeight.Normal,
    placeholder: String = "",
    textColor: Color = Color.DarkGray,
    placeholderColor: Color = Color.Gray,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = textColor,
            fontSize = fontSize.sp,
            fontWeight = fontWeight
        ),
        singleLine = false,
        keyboardOptions = keyboardOptions.copy(
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions = keyboardActions,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp) // altura mínima estilo textarea
            .padding(16.dp),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        color = placeholderColor,
                        fontSize = fontSize.sp,
                        fontWeight = fontWeight
                    )
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun PlanuraTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    fontSize: Int = 16,
    fontWeight: FontWeight = FontWeight.Bold,
    textColor: Color = Color.DarkGray,
    placeholderColor: Color = Color.Gray,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = placeholderColor,
                fontSize = fontSize.sp,
                fontWeight = fontWeight
            )
        },
        singleLine = singleLine,
        textStyle = TextStyle(
            color = textColor,
            fontSize = fontSize.sp,
            fontWeight = fontWeight
        ),
        keyboardOptions = keyboardOptions.copy(capitalization = KeyboardCapitalization.Sentences),
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun Components() {
    PlanuraAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            PlanuraTextField(
                value = "",
                onValueChange = { },
                placeholder = "Título"
            )

            PlanuraTextArea(
                value = "",
                onValueChange = {},
                placeholder = "Escribe algo aquí..."
            )
        }
    }
}