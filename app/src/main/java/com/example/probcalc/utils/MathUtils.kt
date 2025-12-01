package com.example.probcalc.utils

import kotlin.math.ln
import kotlin.math.exp
import kotlin.math.pow

/**
 * Утилиты для комбинаторных вычислений и теории вероятностей
 *
 * ФОРМУЛЫ ИЗ ЗАДАНИЯ:
 *
 * РАЗМЕЩЕНИЯ:
 * - Без повторений: A(n,k) = n! / (n-k)!
 * - С повторениями: Ā(n,k) = n^k
 *
 * ПЕРЕСТАНОВКИ:
 * - Без повторений: P(n) = n!
 * - С повторениями: P(n; n₁,n₂,...,nₖ) = n! / (n₁! × n₂! × ... × nₖ!)
 *   где n₁ + n₂ + ... + nₖ = n
 *
 * СОЧЕТАНИЯ:
 * - Без повторений: C(n,k) = n! / (k! × (n-k)!)
 * - С повторениями: C̄(n,k) = C(n+k-1, k)
 */
object MathUtils {

    // ==================== ОСНОВНЫЕ ФУНКЦИИ ====================

    /**
     * Вычисление факториала n!
     * Использует логарифмы для избежания переполнения
     */
    fun factorial(n: Int): Double {
        require(n >= 0) { "Factorial is not defined for negative numbers" }
        if (n == 0 || n == 1) return 1.0

        var result = 0.0
        for (i in 2..n) {
            result += ln(i.toDouble())
        }
        return exp(result)
    }

    // ==================== РАЗМЕЩЕНИЯ ====================

    /**
     * Размещения без повторений
     * Формула: A(n,k) = n! / (n-k)!
     *
     * Пример: A(5,3) = 5!/(5-3)! = 5×4×3 = 60
     */
    fun arrangements(n: Int, k: Int): Double {
        require(n >= 0 && k >= 0) { "n and k must be non-negative" }
        require(k <= n) { "k cannot be greater than n" }

        if (k == 0) return 1.0

        var result = 1.0
        for (i in 0 until k) {
            result *= (n - i)
        }
        return result
    }

    /**
     * Размещения с повторениями
     * Формула: Ā(n,k) = n^k
     *
     * Пример: Ā(5,3) = 5³ = 125
     */
    fun arrangementsWithRepetition(n: Int, k: Int): Double {
        require(n >= 0 && k >= 0) { "n and k must be non-negative" }
        return n.toDouble().pow(k)
    }

    // ==================== ПЕРЕСТАНОВКИ ====================

    /**
     * Перестановки без повторений
     * Формула: P(n) = n!
     *
     * Пример: P(5) = 5! = 120
     */
    fun permutations(n: Int): Double {
        require(n >= 0) { "n must be non-negative" }
        return factorial(n)
    }

    /**
     * Перестановки с повторениями (с одинаковыми элементами)
     * Формула: P(n; n₁,n₂,...,nₖ) = n! / (n₁! × n₂! × ... × nₖ!)
     * где n = n₁ + n₂ + ... + nₖ
     *
     * Пример: Слово "MISSISSIPPI" имеет 11 букв
     * M=1, I=4, S=4, P=2
     * P(11; 1,4,4,2) = 11! / (1! × 4! × 4! × 2!) = 34650
     *
     * @param countsString строка с количествами через запятую, например "1,4,4,2"
     */
    fun permutationsWithRepetition(countsString: String): Double {
        if (countsString.isBlank()) {
            throw IllegalArgumentException("Counts string cannot be empty")
        }

        val counts = countsString.split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() } // Игнорируем пустые элементы
            .map {
                it.toIntOrNull() ?: throw IllegalArgumentException("Invalid number format: '$it'")
            }
            .filter { it > 0 } // Фильтруем нулевые значения (они не влияют на результат)

        if (counts.isEmpty()) {
            throw IllegalArgumentException("At least one positive count must be provided")
        }

        val total = counts.sum()
        if (total == 0) return 1.0

        var denominator = 1.0
        for (count in counts) {
            if (count > 0) {
                denominator *= factorial(count)
            }
        }
        return factorial(total) / denominator
    }

    // ==================== СОЧЕТАНИЯ ====================

    /**
     * Сочетания без повторений
     * Формула: C(n,k) = n! / (k! × (n-k)!)
     *
     * Пример: C(5,3) = 5! / (3! × 2!) = 10
     */
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

    /**
     * Сочетания с повторениями
     * Формула: C̄(n,k) = C(n+k-1, k)
     *
     * Пример: C̄(3,2) = C(3+2-1, 2) = C(4,2) = 6
     */
    fun combinationsWithRepetition(n: Int, k: Int): Double {
        require(n >= 0 && k >= 0) { "n and k must be non-negative" }
        if (n == 0) return if (k == 0) 1.0 else 0.0
        return combinations(n + k - 1, k)
    }

    // ==================== ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ====================

    // Проверка, что числа могут быть использованы в комбинаторике
    fun validateCombinatoricsInput(n: Int, k: Int = 0, type: String = "combinations"): String? {
        return when {
            n < 0 || k < 0 -> "Numbers must be non-negative"
            (type == "arrangements" || type == "combinations") && k > n -> "k cannot be greater than n"
            else -> null
        }
    }

    // Проверка валидности counts для перестановок с повторениями
    fun validatePermutationCounts(counts: List<Int>): String? {
        return when {
            counts.any { it < 0 } -> "All counts must be non-negative"
            counts.isEmpty() -> "At least one count must be provided"
            else -> null
        }
    }

    // Форматирование больших чисел
    fun formatLargeNumber(value: Double): String {
        return when {
            value.isInfinite() -> "∞"
            value.isNaN() -> "Invalid"
            value > 1e12 -> String.format("%.2e", value)
            value == value.toLong().toDouble() -> value.toLong().toString()
            else -> String.format("%.2f", value)
        }
    }

    // ==================== ВЕРОЯТНОСТНЫЕ ФУНКЦИИ ====================

    // Вероятность того, что все k извлеченных предметов меченые
    // P(A) = C(m, k) / C(n, k)
    fun probabilityAllMarked(n: Int, m: Int, k: Int): Double {
        require(n >= 0 && m >= 0 && k >= 0) { "All parameters must be non-negative" }
        require(m <= n) { "m cannot be greater than n" }
        require(k <= n) { "k cannot be greater than n" }
        require(k <= m) { "k cannot be greater than m" }

        val numerator = combinations(m, k)
        val denominator = combinations(n, k)
        return numerator / denominator
    }

    // Вероятность того, что среди k извлеченных предметов ровно r меченых
    // P(A) = C(m, r) * C(n - m, k - r) / C(n, k)
    fun probabilityExactlyRMarked(n: Int, m: Int, k: Int, r: Int): Double {
        require(n >= 0 && m >= 0 && k >= 0 && r >= 0) { "All parameters must be non-negative" }
        require(m <= n) { "m cannot be greater than n" }
        require(k <= n) { "k cannot be greater than n" }
        require(r <= m) { "r cannot be greater than m" }
        require(r <= k) { "r cannot be greater than k" }
        require(k - r <= n - m) { "k - r cannot be greater than n - m" }

        val numerator = combinations(m, r) * combinations(n - m, k - r)
        val denominator = combinations(n, k)
        return numerator / denominator
    }
}