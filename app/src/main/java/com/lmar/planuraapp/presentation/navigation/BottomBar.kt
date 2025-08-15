package com.lmar.planuraapp.presentation.navigation

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController,
    destinations: List<BottomItem>,
    reminderCount: Int
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val currentDestination = navController
            .currentBackStackEntryAsState().value?.destination

        destinations.forEach { item ->
            val selected = currentDestination?.hierarchy
                ?.any { it.route == item.route.route } == true

            val colorItem = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route.route) {
                        // Mantiene estado al cambiar de pestaña
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (item.route is AppRoutes.ReminderScreen && reminderCount > 0) {
                        BadgedBox(
                            badge = { Badge { Text(reminderCount.toString()) } }
                        ) {
                            Icon(
                                item.icon,
                                contentDescription = item.route.label,
                                tint = colorItem
                            )
                        }
                    } else {
                        Icon(
                            item.icon,
                            contentDescription = item.route.label,
                            tint = colorItem
                        )
                    }
                },
                label = { Text(item.route.label, color = colorItem) }
            )
        }

    }
}