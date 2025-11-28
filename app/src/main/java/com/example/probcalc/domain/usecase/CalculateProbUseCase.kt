package com.example.probcalc.domain.usecase

import com.example.probcalc.presentation.screens.prob.ProbScreenState
import com.example.probcalc.utils.MathUtils
import javax.inject.Inject

class CalculateProbUseCase @Inject constructor() {

    operator fun invoke(state: ProbScreenState): String {
        return try {
            val n = state.n.toIntOrNull()
            val k = state.k.toIntOrNull()
            val m = state.m.toIntOrNull()
            val r = state.r.toIntOrNull()

            // Basic validation
            if (n == null || k == null || m == null) {
                return "Please enter valid numbers"
            }

            if (n < 0 || k < 0 || m < 0) {
                return "Numbers must be non-negative"
            }

            if (k > n) {
                return "k cannot be greater than n"
            }

            if (m > n) {
                return "m cannot be greater than n"
            }

            val result = if (!state.allMarked) {
                // Case a) Все извлеченные предметы меченые
                // P(A) = C(m, k) / C(n, k)
                if (k > m) {
                    return "k cannot be greater than m when all marked"
                }
                val numerator = MathUtils.combinations(m, k)
                val denominator = MathUtils.combinations(n, k)
                numerator / denominator
            } else {
                // Case b) Среди извлеченных r меченых
                // P(A) = C(m, r) * C(n - m, k - r) / C(n, k)
                if (r == null) {
                    return "Please enter r value"
                }
                if (r < 0) {
                    return "r must be non-negative"
                }
                if (r > m) {
                    return "r cannot be greater than m"
                }
                if (r > k) {
                    return "r cannot be greater than k"
                }
                if (k - r > n - m) {
                    return "k - r cannot be greater than n - m"
                }

                val numerator = MathUtils.combinations(m, r) * MathUtils.combinations(n - m, k - r)
                val denominator = MathUtils.combinations(n, k)
                numerator / denominator
            }

            formatProbability(result)
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        } catch (e: Exception) {
            "Calculation error"
        }
    }

    private fun formatProbability(probability: Double): String {
        return when {
            probability < 0 -> "Invalid probability"
            probability > 1 -> "Probability cannot exceed 1"
            probability == 0.0 -> "0"
            probability == 1.0 -> "1"
            probability < 0.0001 -> String.format("%.2e", probability)
            else -> String.format("%.6f", probability)
        }
    }
}