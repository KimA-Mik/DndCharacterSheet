package ru.kima.dndcharactersheet.ui.editCharacter.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.model.CharactersDatabaseService

class EditCharacterInfoViewModel(characterId: Int) :
    ViewModel(),
    KoinComponent,
    InfoUpdateListener {
    private val database: CharactersDatabaseService by inject()

    private val _character = MutableStateFlow(CharacterEntity())
    val character = _character.asStateFlow()
    private val _needSave = MutableStateFlow(false)
    val needSave = _needSave.asStateFlow()

    private val changedInfo = mutableMapOf<InfoType, Boolean>()


    init {
        viewModelScope.launch {
            val char = database.getCharacterById(characterId)
            char?.let {
                _character.value = it
            }
        }
    }

    fun onSave(
        name: String,
        race: String,
        characterClass: String,
        armorClass: String,
        speed: String,
        initiative: String
    ) {
        viewModelScope.launch {
            _character.update {
                it.copy(
                    name = name.trim(),
                    race = race.trim(),
                    charClass = characterClass.trim(),
                    armorClass = armorClass.trim(),
                    speed = speed.trim(),
                    initiative = initiative.trim()
                )
            }
            database.updateCharacter(_character.value)
        }
    }

    override fun onInfoUpdated(type: InfoType, info: String) {
        val oldInfo = when (type) {
            InfoType.NAME -> _character.value.name
            InfoType.RACE -> _character.value.race
            InfoType.CLASS -> _character.value.charClass
            InfoType.AC -> _character.value.armorClass
            InfoType.SPEED -> _character.value.speed
            InfoType.INITIATIVE -> _character.value.initiative
        }

        if (oldInfo != info) {
            changedInfo[type] = true
            _needSave.update { true }
            return
        }

        changedInfo[type] = false
        _needSave.update {
            changedInfo.any { (_, modified) -> modified }
        }
    }

}