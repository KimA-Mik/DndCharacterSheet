package ru.kima.dndcharactersheet.ui.sheet

import androidx.lifecycle.ViewModel
import ru.kima.dndcharactersheet.model.CharactersDatabaseService

class CharacterSheetViewModel(
    private val database: CharactersDatabaseService
) : ViewModel() {
    fun loadCharacter(i: Int) {
        //TODO
    }
}