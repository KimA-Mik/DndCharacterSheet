package ru.kima.dndcharactersheet.ui.sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.dnd.DndUtilities
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.ui.sheet.event.EventRoll
import ru.kima.dndcharactersheet.ui.sheet.event.RollType
import ru.kima.dndcharactersheet.ui.sheet.event.RollValue
import ru.kima.dndcharactersheet.ui.sheet.pages.listeners.CharacteristicsAndAbilitiesListener
import kotlin.random.Random

class CharacterSheetViewModel() :
    ViewModel(),
    CharacteristicsAndAbilitiesListener,
    KoinComponent {

    private val database: CharactersDatabaseService by inject()
    private val _character = MutableStateFlow(CharacterEntity())
    val character = _character.asStateFlow()
    private val _tobBarState = MutableStateFlow(TopBarState.EXPANDED)
    val topBarState = _tobBarState.asStateFlow()
    private val _rollEvent = MutableSharedFlow<EventRoll>()
    val rollEvent = _rollEvent.asSharedFlow()

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

    override fun onRoll(type: RollType, value: RollValue) {
        viewModelScope.launch {
            val eventRoll = EventRoll(
                type,
                value,
                Random.nextInt(0, 100)
            )
            _rollEvent.emit(eventRoll)
        }
    }

    override fun onRollEvent(roll: EventRoll) {
        viewModelScope.launch {
            _rollEvent.emit(roll)
        }
    }
}