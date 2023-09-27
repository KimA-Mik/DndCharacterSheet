package ru.kima.dndcharactersheet.dnd

import kotlin.math.floor

class DndUtilities {
    private val levelLookupTable = intArrayOf(
        0,
        300,
        900,
        2700,
        6500,
        14000,
        23000,
        34000,
        48000,
        64000,
        85000,
        100000,
        120000,
        140000,
        165000,
        195000,
        225000,
        265000,
        305000,
        355000
    )

    fun getXpToLvlUp(level: Int): Int {
        return levelLookupTable[level - 1]
    }

    fun getCharacteristicsModifier(characteristic: Int): Int {
        //Модификатор характеристики: (Значение характеристики - 10) / 2. Округление в меньшую сторону.
        return floor((characteristic - 10) / 2f).toInt()
    }
}