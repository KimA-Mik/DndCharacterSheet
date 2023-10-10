package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndAbilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.ui.sheet.event.RollType
import ru.kima.dndcharactersheet.ui.sheet.event.RollValue
import ru.kima.dndcharactersheet.ui.sheet.pages.listeners.CharacteristicsAndAbilitiesListener

class CharacteristicsAndAbilitiesViewModel(
    private val listener: CharacteristicsAndAbilitiesListener
) :
    ViewModel(),
    KoinComponent {
    private val databaseService: CharactersDatabaseService by inject()

    fun roll() = viewModelScope.launch {
        listener.onRoll(RollType.CHECK, RollValue.STRENGTH)
    }
}