package com.example.probcalc.domain.usecase

import com.example.probcalc.presentation.screens.comb.CombScreenState
import javax.inject.Inject

import com.example.probcalc.domain.model.CalcType
import com.example.probcalc.utils.MathUtils

class CalculateCombUseCase @Inject constructor() {

    operator fun invoke(state: CombScreenState): String {
        return try {
            val n = state.n.toIntOrNull()
            val k = state.k.toIntOrNull()

            if (n == null || k == null) {
                return "Please enter valid numbers"
            }

            if (n < 0 || k < 0) {
                return "Numbers must be non-negative"
            }

            val result = when (state.type) {
                CalcType.PLACEMENT -> {
                    if (state.withRepeats) {
                        MathUtils.permutationsWithRepetition(n, k)
                    } else {
                        MathUtils.permutations(n, k)
                    }
                }
                CalcType.PERMUTATION -> {
                    if (state.withRepeats) {
                        // Для перестановок с повторениями нужны группы
                        // В этом упрощенном случае считаем как обычные перестановки
                        if (k != n) {
                            return "For permutations with repeats, k must equal n"
                        }
                        MathUtils.factorial(n)
                    } else {
                        if (k != n) {
                            return "For permutations without repeats, k must equal n"
                        }
                        MathUtils.factorial(n)
                    }
                }
                CalcType.COMBINATION -> {
                    if (state.withRepeats) {
                        MathUtils.combinationsWithRepetition(n, k)
                    } else {
                        MathUtils.combinations(n, k)
                    }
                }
            }

            formatResult(result)
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        } catch (e: Exception) {
            "Calculation error"
        }
    }

    private fun formatResult(value: Double): String {
        return if (value > 1e12) {
            String.format("%.2e", value)
        } else if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            String.format("%.2f", value)
        }
    }
}