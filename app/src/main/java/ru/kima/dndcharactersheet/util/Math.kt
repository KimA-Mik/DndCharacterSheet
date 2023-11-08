package ru.kima.dndcharactersheet.util

import kotlin.math.abs

object Math {

    fun toSignedString(value: Int): String {
        return when {
            isPositive(value) -> "+$value"

            isNegative(value) -> "-${abs(value)}"

            else -> value.toString()
        }
    }

    fun isPositive(num: Int) = num > 0
    fun isNegative(num: Int) = num < 0
}