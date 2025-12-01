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
            // Валидация основных параметров
            val n = state.n.toIntOrNull()
            val m = state.m.toIntOrNull()
            val k = state.k.toIntOrNull()

            if (n == null || m == null || k == null) {
                return resourceProvider.getString(R.string.please_enter_valid_numbers)
            }

            if (n < 0 || m < 0 || k < 0) {
                return resourceProvider.getString(R.string.numbers_must_be_non_negative)
            }

            if (k > n) {
                return resourceProvider.getString(R.string.k_cannot_be_greater_than_n)
            }

            if (m > n) {
                return resourceProvider.getString(R.string.m_cannot_be_greater_than_n)
            }

            val result = if (state.allMarked) {
                // Случай a) Все извлеченные предметы меченые
                calculateAllMarkedProbability(n, m, k)
            } else {
                // Случай b) Среди извлеченных ровно r меченых
                calculateExactlyRMarkedProbability(n, m, k, state)
            }

            formatProbability(result)
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        } catch (e: ArithmeticException) {
            resourceProvider.getString(R.string.calculation_error_overflow)
        } catch (e: Exception) {
            resourceProvider.getString(R.string.calculation_error)
        }
    }

    private fun calculateAllMarkedProbability(n: Int, m: Int, k: Int): Double {
        // P(A) = C(m, k) / C(n, k)
        if (k > m) {
            throw IllegalArgumentException(
                resourceProvider.getString(R.string.k_cannot_be_greater_than_m_when_all_marked)
            )
        }

        return MathUtils.probabilityAllMarked(n, m, k)
    }

    private fun calculateExactlyRMarkedProbability(n: Int, m: Int, k: Int, state: ProbScreenState): Double {
        // P(A) = C(m, r) * C(n - m, k - r) / C(n, k)
        val r = state.r.toIntOrNull()

        if (r == null) {
            throw IllegalArgumentException(
                resourceProvider.getString(R.string.please_enter_r_value)
            )
        }

        if (r < 0) {
            throw IllegalArgumentException(
                resourceProvider.getString(R.string.r_must_be_non_negative)
            )
        }

        if (r > m) {
            throw IllegalArgumentException(
                resourceProvider.getString(R.string.r_cannot_be_greater_than_m)
            )
        }

        if (r > k) {
            throw IllegalArgumentException(
                resourceProvider.getString(R.string.r_cannot_be_greater_than_k)
            )
        }

        if (k - r > n - m) {
            throw IllegalArgumentException(
                resourceProvider.getString(R.string.k_r_cannot_be_greater_than_n_m)
            )
        }

        return MathUtils.probabilityExactlyRMarked(n, m, k, r)
    }

    private fun formatProbability(probability: Double): String {
        return when {
            probability < 0 -> resourceProvider.getString(R.string.invalid_probability)
            probability > 1 -> resourceProvider.getString(R.string.probability_cannot_exceed_1)
            probability == 0.0 -> "0"
            probability == 1.0 -> "1"
            probability < 0.0001 -> String.format("%.2e", probability)
            probability < 0.001 -> String.format("%.6f", probability)
            probability < 0.01 -> String.format("%.5f", probability)
            else -> String.format("%.4f", probability)
        }
    }
}