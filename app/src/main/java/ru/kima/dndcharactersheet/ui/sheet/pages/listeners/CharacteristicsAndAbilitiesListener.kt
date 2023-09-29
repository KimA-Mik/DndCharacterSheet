package ru.kima.dndcharactersheet.ui.sheet.pages.listeners

import ru.kima.dndcharactersheet.ui.sheet.event.RollType
import ru.kima.dndcharactersheet.ui.sheet.event.RollValue

interface CharacteristicsAndAbilitiesListener {
    fun onRoll(type: RollType, value: RollValue)
}