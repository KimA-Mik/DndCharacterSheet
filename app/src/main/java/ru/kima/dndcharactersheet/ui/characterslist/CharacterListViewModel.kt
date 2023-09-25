package ru.kima.dndcharactersheet.ui.characterslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.util.Event

class CharacterListViewModel(private val database: CharactersDatabaseService) :
    ViewModel(), CharacterListListener {

    private val _showSheet = MutableStateFlow<Event<Int?>>(Event(null))
    val showSheet = _showSheet.asStateFlow()

    private val charactersList = listOf(
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

    init {
        loadCharacters()
    }

    fun createCharacter() = viewModelScope.launch(Dispatchers.IO) {
        val character = CharacterEntity(
            name = "New character"
        )
        database.addCharacter(character)
        val characters = database.getAllCharacters()
        _characters.value = characters
    }

    override fun onListItemClicked(charId: Int) {
        _showSheet.value = Event(charId)
    }

    private fun loadCharacters() = viewModelScope.launch(Dispatchers.IO) {
        val characters = database.getAllCharacters()
        _characters.value = characters
    }
}