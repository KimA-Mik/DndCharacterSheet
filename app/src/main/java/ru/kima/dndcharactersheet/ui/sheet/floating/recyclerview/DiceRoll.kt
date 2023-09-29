package ru.kima.dndcharactersheet.ui.sheet.floating.recyclerview

import ru.kima.dndcharactersheet.ui.sheet.event.RollType
import ru.kima.dndcharactersheet.ui.sheet.event.RollValue

data class DiceRoll(
    val sum: Int,
    val results: String,
    val dice: String,
    val type: RollType,
    val value: RollValue,
)
