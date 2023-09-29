package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndAbilities

import androidx.lifecycle.ViewModel
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.ui.sheet.event.RollType
import ru.kima.dndcharactersheet.ui.sheet.event.RollValue
import ru.kima.dndcharactersheet.ui.sheet.pages.listeners.CharacteristicsAndAbilitiesListener

class CharacteristicsAndAbilitiesViewModel(
    private val listener: CharacteristicsAndAbilitiesListener,
    private val databaseService: CharactersDatabaseService
) : ViewModel() {
    fun roll() {
        listener.onRoll(RollType.CHECK, RollValue.STRENGTH)
    }
}