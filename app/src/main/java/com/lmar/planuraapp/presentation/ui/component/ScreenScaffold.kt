package com.lmar.planuraapp.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenScaffold(
    title: String,
    withFAB: Boolean = false,
    onFABClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 150.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TopAppBar(
            title = { Text(title) },
            actions = actions
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Box {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                content()
            }

            if (withFAB) {
                FloatingActionButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = onFABClick,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "New Note"
                    )
                }
            }
        }
    }
}