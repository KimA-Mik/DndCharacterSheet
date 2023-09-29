package ru.kima.dndcharactersheet.ui.sheet.event

data class EventRoll(
    val type: RollType = RollType.NONE,
    val value: RollValue = RollValue.NONE,
    val modifier: Int = 0
)
