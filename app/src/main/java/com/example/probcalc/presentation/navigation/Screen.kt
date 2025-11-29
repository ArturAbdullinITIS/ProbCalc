package com.example.probcalc.presentation.navigation


object Routes {
    const val COMB = "combinatorics"
    const val PROB = "probability"
}

sealed class Screen(val route: String) {
    object Combinatorics: Screen(Routes.COMB)
    object Probability: Screen(Routes.PROB)
}