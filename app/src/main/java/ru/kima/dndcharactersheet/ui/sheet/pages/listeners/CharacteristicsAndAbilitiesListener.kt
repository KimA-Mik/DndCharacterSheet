package ru.kima.dndcharactersheet.ui.sheet.pages.listeners

import ru.kima.dndcharactersheet.ui.sheet.event.EventRoll

interface CharacteristicsAndAbilitiesListener {
    fun onRoll(type: EventRoll.Type, value: EventRoll.Value)
    fun onRollEvent(roll: EventRoll)
}