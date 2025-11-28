package com.example.probcalc.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.probcalc.presentation.screens.comb.CombScreen
import com.example.probcalc.presentation.screens.prob.ProbScreen
import com.example.probcalc.presentation.ui.theme.Contrast_circle
import com.example.probcalc.presentation.ui.theme.Percent

@Composable
fun NavBar() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = Screen.Combinatorics.route
        ) {
            composable(Screen.Combinatorics.route) {
                CombScreen()
            }
            composable(Screen.Probability.route) {
                ProbScreen()
            }
        }

    }
}



@Composable
fun BottomNavBar(navController: NavController) {
    val screens = listOf(
        Screen.Combinatorics,
        Screen.Probability
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface),
        tonalElevation = 8.dp,
    ) {
        screens.forEach { screen ->

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Combinatorics -> Contrast_circle
                            Screen.Probability -> Percent
                        },
                        contentDescription = screen.route
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route)
                },
                label = { Text(getScreenTitle(screen)) }
            )
        }
    }
}

@Composable
private fun getScreenTitle(screen: Screen): String {
    return when(screen) {
        Screen.Combinatorics -> "combinatorics"
        Screen.Probability -> "probability"
    }
}