package com.lmar.planuraapp.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanuraAppTheme {
                AppNavigation()
            }
        }
    }
}