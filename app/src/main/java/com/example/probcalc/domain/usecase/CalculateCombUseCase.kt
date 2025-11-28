package com.example.probcalc.domain.usecase

import com.example.probcalc.presentation.screens.comb.CombScreenState
import javax.inject.Inject

class CalculateCombUseCase @Inject constructor(){

    operator fun invoke(state: CombScreenState): String {
        return "Test"
    }
}