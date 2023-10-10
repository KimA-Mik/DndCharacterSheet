package ru.kima.dndcharactersheet.ui.characterslist

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
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.ui.characterslist.menu.CharacterListMenuListener
import ru.kima.dndcharactersheet.ui.characterslist.recyclerview.SwipeListener

class CharacterListViewModel :
    ViewModel(),
    KoinComponent,
    CharacterListListener,
    SwipeListener,
    CharacterListMenuListener {

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()
    private val database: CharactersDatabaseService by inject()

    private val charactersList = listOf(
        CharacterEntity(
            name = "DJ Жаба",
            race = "Грунг",
            charClass = "Плут",
            currentHp = 8,
            maxHp = 8,
            level = 3,
            experiencePoints = 1080,
            armorClass = 16
        ),
        CharacterEntity(
            name = "Болт",
            race = "Кобольт",
            charClass = "Техножрец",
            currentHp = 1,
            maxHp = 12,
            level = 1,
            experiencePoints = 150,
            armorClass = 14
        )
    )
    private var allCharacters = emptyList<CharacterEntity>()
    private val _characters = MutableStateFlow(charactersList)
    val characters = _characters.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadCharacters()
            //TODO: remove debug elements
            if (_characters.value.isEmpty()) {
                charactersList.forEach {
                    database.addCharacter(it)
                }
                loadCharacters()
            }
        }
    }

    fun createCharacter() = viewModelScope.launch(Dispatchers.IO) {
        val character = CharacterEntity(
            name = "New character"
        )
        database.addCharacter(character)
        loadCharacters()
    }

    override fun onListItemClicked(charId: Int) {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.ShowSheet(charId))
        }
    }

    private suspend fun loadCharacters() {
        allCharacters = database.getAllCharacters()
        _characters.value = allCharacters
    }

    override fun onItemDismiss(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val elementId = _characters.value[position].id
            database.deleteCharacterById(elementId)
            loadCharacters()
        }
    }

    fun onQuery(query: String) {
        if (query.isEmpty()) {
            _characters.value = allCharacters
            return
        }
        _characters.value = allCharacters.filter { it.name.contains(query, true) }
    }

    override fun onOpenSetting() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.Settings())
        }
    }
}