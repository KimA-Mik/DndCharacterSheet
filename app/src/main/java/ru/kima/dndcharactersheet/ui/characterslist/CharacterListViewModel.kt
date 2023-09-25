package ru.kima.dndcharactersheet.ui.characterslist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.util.Event

class CharacterListViewModel(private val database: CharactersDatabaseService) :
    ViewModel(), CharacterListListener {

    private val _showSheet = MutableStateFlow<Event<Int?>>(Event(null))
    val showSheet = _showSheet.asStateFlow()

    val charactersList = listOf(
        CharacterEntity(
            name = "DJ Жаба",
            race = "Грунг",
            charClass = "Плут",
            currentHp = 8,
            maxHp = 8
        ),
        CharacterEntity(
            name = "Болт",
            race = "Кобольт",
            charClass = "Техножрец",
            currentHp = 1,
            maxHp = 12
        )
    )
    private val _characters = MutableStateFlow(charactersList)
    val characters = _characters.asStateFlow()


    override fun onListItemClicked(charId: Int) {
        _showSheet.value = Event(charId)
    }
}