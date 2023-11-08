package ru.kima.dndcharactersheet.ui.sheet.floating.recyclerview

import ru.kima.dndcharactersheet.ui.sheet.event.EventRoll

data class DiceRoll(
    val sum: Int,
    val results: String,
    val dice: String,
    val type: EventRoll.Type,
    val value: EventRoll.Value,
)
