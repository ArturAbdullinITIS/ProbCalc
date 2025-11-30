package com.example.probcalc.domain.usecase

import com.example.probcalc.presentation.screens.prob.ProbScreenState
import com.example.probcalc.utils.MathUtils
import com.example.probcalc.utils.ResourceProvider
import ru.itis.notifications.R
import javax.inject.Inject

class CalculateProbUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    operator fun invoke(state: ProbScreenState): String {
        return try {
            val n = state.n.toIntOrNull()
            val k = state.k.toIntOrNull()
            val m = state.m.toIntOrNull()
            val r = state.r.toIntOrNull()

            // Basic validation
            if (n == null || k == null || m == null) {
                return resourceProvider.getString(R.string.please_enter_valid_numbers)
            }

            if (n < 0 || k < 0 || m < 0) {
                return resourceProvider.getString(R.string.numbers_must_be_non_negative)
            }

            if (k > n) {
                return resourceProvider.getString(R.string.k_cannot_be_greater_than_n)
            }

            if (m > n) {
                return resourceProvider.getString(R.string.m_cannot_be_greater_than_n)
            }

            val result = if (state.allMarked) {
                // Case a) Все извлеченные предметы меченые
                // P(A) = C(m, k) / C(n, k)
                if (k > m) {
                    return resourceProvider.getString(R.string.k_cannot_be_greater_than_m_when_all_marked)
                }
                val numerator = MathUtils.combinations(m, k)
                val denominator = MathUtils.combinations(n, k)
                numerator / denominator
            } else {
                // Case b) Среди извлеченных r меченых
                // P(A) = C(m, r) * C(n - m, k - r) / C(n, k)
                if (r == null) {
                    return resourceProvider.getString(R.string.please_enter_r_value)
                }
                if (r < 0) {
                    return resourceProvider.getString(R.string.r_must_be_non_negative)
                }
                if (r > m) {
                    return resourceProvider.getString(R.string.r_cannot_be_greater_than_m)
                }
                if (r > k) {
                    return resourceProvider.getString(R.string.r_cannot_be_greater_than_k)
                }
                if (k - r > n - m) {
                    return resourceProvider.getString(R.string.k_r_cannot_be_greater_than_n_m)
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
            probability < 0 -> resourceProvider.getString(R.string.invalid_probability)
            probability > 1 -> resourceProvider.getString(R.string.probability_cannot_exceed_1)
            probability == 0.0 -> "0"
            probability == 1.0 -> "1"
            probability < 0.0001 -> String.format("%.2e", probability)
            else -> String.format("%.6f", probability)
        }
    }
}