package com.example.probcalc.domain.usecase

import com.example.probcalc.presentation.screens.comb.CombScreenState
import javax.inject.Inject

import com.example.probcalc.domain.model.CalcType
import com.example.probcalc.utils.MathUtils
import com.example.probcalc.utils.ResourceProvider
import ru.itis.notifications.R

class CalculateCombUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    operator fun invoke(state: CombScreenState): String {
        return try {
            val n = state.n.toIntOrNull()
            val k = state.k.toIntOrNull()

            // Проверка на отрицательные числа
            if (n != null && n < 0) {
                return resourceProvider.getString(R.string.numbers_must_be_non_negative)
            }
            if (k != null && k < 0) {
                return resourceProvider.getString(R.string.numbers_must_be_non_negative)
            }

            val result = when (state.type) {
                // РАЗМЕЩЕНИЯ
                CalcType.PLACEMENT -> {
                    if (n == null || k == null) {
                        return resourceProvider.getString(R.string.please_enter_valid_numbers)
                    }
                    if (state.withRepeats) {
                        // Размещения с повторениями: Ā(n,k) = n^k
                        MathUtils.arrangementsWithRepetition(n, k)
                    } else {
                        // Размещения без повторений: A(n,k) = n!/(n-k)!
                        MathUtils.arrangements(n, k)
                    }
                }
                // ПЕРЕСТАНОВКИ
                CalcType.PERMUTATION -> {
                    if (n == null) {
                        return resourceProvider.getString(R.string.please_enter_valid_numbers)
                    }
                    if (state.withRepeats) {
                        // Перестановки с повторениями: P(n; n₁,n₂,...,nₖ) = n!/(n₁!×n₂!×...×nₖ!)
                        if (state.counts.isEmpty()) {
                            return "Для перестановок с повторениями введите количества через запятую"
                        }

                        // Проверяем, что сумма counts равна n
                        try {
                            // Поддерживаем как запятые, так и пробелы в качестве разделителей
                            val counts = state.counts
                                .replace(",", " ")  // Заменяем запятые на пробелы
                                .split(" ")         // Разделяем по пробелам
                                .map { it.trim() }
                                .filter { it.isNotBlank() }
                                .map { it.toInt() }

                            if (counts.isEmpty()) {
                                return "Введите хотя бы одно число"
                            }

                            val sum = counts.sum()
                            if (sum != n) {
                                return "Ошибка: сумма групп ($sum) должна равняться n ($n)"
                            }

                            // Передаем строку с запятыми для MathUtils
                            val countsString = counts.joinToString(",")
                            MathUtils.permutationsWithRepetition(countsString)
                        } catch (e: Exception) {
                            return "Ошибка: неверный формат чисел"
                        }
                    } else {
                        // Перестановки без повторений: P(n) = n!
                        MathUtils.permutations(n)
                    }
                }
                // СОЧЕТАНИЯ
                CalcType.COMBINATION -> {
                    if (n == null || k == null) {
                        return resourceProvider.getString(R.string.please_enter_valid_numbers)
                    }
                    if (state.withRepeats) {
                        // Сочетания с повторениями: C̄(n,k) = C(n+k-1, k)
                        MathUtils.combinationsWithRepetition(n, k)
                    } else {
                        // Сочетания без повторений: C(n,k) = n!/(k!×(n-k)!)
                        MathUtils.combinations(n, k)
                    }
                }
            }

            formatResult(result)
        } catch (e: IllegalArgumentException) {
            "Ошибка: ${e.message}"
        } catch (e: Exception) {
            "Ошибка вычисления"
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