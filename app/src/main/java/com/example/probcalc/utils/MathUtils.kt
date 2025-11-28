package com.example.probcalc.utils

import kotlin.math.ln
import kotlin.math.exp

object MathUtils {

    // Вычисление факториала с использованием логарифмов для больших чисел
    fun factorial(n: Int): Double {
        require(n >= 0) { "Factorial is not defined for negative numbers" }
        if (n == 0 || n == 1) return 1.0

        var result = 0.0
        for (i in 2..n) {
            result += ln(i.toDouble())
        }
        return exp(result)
    }

    // Сочетания без повторений C(n, k) = n! / (k! * (n - k)!)
    fun combinations(n: Int, k: Int): Double {
        require(n >= 0 && k >= 0) { "n and k must be non-negative" }
        require(k <= n) { "k cannot be greater than n" }

        if (k == 0 || k == n) return 1.0
        if (k == 1 || k == n - 1) return n.toDouble()

        // Используем логарифмы для избежания переполнения
        var logResult = 0.0
        for (i in 1..k) {
            logResult += ln((n - k + i).toDouble()) - ln(i.toDouble())
        }
        return exp(logResult)
    }

    // Размещения без повторений A(n, k) = n! / (n - k)!
    fun permutations(n: Int, k: Int): Double {
        require(n >= 0 && k >= 0) { "n and k must be non-negative" }
        require(k <= n) { "k cannot be greater than n" }

        if (k == 0) return 1.0

        var result = 1.0
        for (i in 0 until k) {
            result *= (n - i)
        }
        return result
    }

    // Размещения с повторениями A(n, k) = n^k
    fun permutationsWithRepetition(n: Int, k: Int): Double {
        require(n >= 0 && k >= 0) { "n and k must be non-negative" }
        return Math.pow(n.toDouble(), k.toDouble())
    }

    // Сочетания с повторениями C(n, k) = C(n + k - 1, k)
    fun combinationsWithRepetition(n: Int, k: Int): Double {
        require(n >= 0 && k >= 0) { "n and k must be non-negative" }
        return combinations(n + k - 1, k)
    }

    // Перестановки с повторениями P(n; n1, n2, ..., nk) = n! / (n1! * n2! * ... * nk!)
    fun permutationsWithIdentical(n: Int, groups: List<Int>): Double {
        require(n >= 0) { "n must be non-negative" }
        require(groups.sum() == n) { "Sum of groups must equal n" }
        require(groups.all { it >= 0 }) { "All group sizes must be non-negative" }

        var result = factorial(n)
        groups.forEach { groupSize ->
            result /= factorial(groupSize)
        }
        return result
    }
}