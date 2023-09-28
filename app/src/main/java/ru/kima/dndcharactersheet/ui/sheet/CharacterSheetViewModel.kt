package ru.kima.dndcharactersheet.ui.sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.dnd.DndUtilities
import ru.kima.dndcharactersheet.model.CharactersDatabaseService

class CharacterSheetViewModel(
    private val database: CharactersDatabaseService
) : ViewModel() {
    private val _character = MutableStateFlow(CharacterEntity())
    val character = _character.asStateFlow()
    private val _tobBarState = MutableStateFlow(TopBarState.EXPANDED)
    val topBarState = _tobBarState.asStateFlow()

    val dndUtilities = DndUtilities()

    fun loadCharacter(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        database.getCharacterById(id)?.let { characterEntity ->
            _character.value = characterEntity
        }
    }

    fun updateCharXp(curCharXp: Int) {
        val char = _character.value.copy(experiencePoints = curCharXp)
        _character.value = char
    }

    fun onCollapseButtonClicked() {
        val currentState = _tobBarState.value
        _tobBarState.value = if (currentState == TopBarState.EXPANDED)
            TopBarState.COLLAPSED
        else
            TopBarState.EXPANDED
    }

    companion object {
        enum class TopBarState {
            EXPANDED,
            COLLAPSED
        }
    }
}