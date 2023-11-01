package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.kima.dndcharactersheet.dnd.DndUtilities
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.ui.sheet.event.EventRoll
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Characteristic
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.CharacteristicListener
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Converter
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillViewListener
import ru.kima.dndcharactersheet.ui.sheet.pages.listeners.CharacteristicsAndAbilitiesListener

class CharacteristicsAndSkillsViewModel(
    private val listener: CharacteristicsAndAbilitiesListener
) : ViewModel(), KoinComponent, SkillViewListener, CharacteristicListener {
    private val databaseService: CharactersDatabaseService by inject()

    private val _characteristics = MutableStateFlow(Converter.defaultCharacteristics)
    val characteristic = _characteristics.asStateFlow()

    private var reverseIndex = mapOf<Skill.Type, Pair<Int, Int>>()
    private var characterId = 0

    fun load(id: Int) {
        if (characterId != 0) {
            return
        }

        characterId = id
        viewModelScope.launch {
            val characteristicsEntity = databaseService.getCharacteristicsAndSkills(characterId)
            _characteristics.value = Converter.entityToCharacteristics(characteristicsEntity)
            val indexMap = mutableMapOf<Skill.Type, Pair<Int, Int>>()
            for (i in _characteristics.value.indices) {
                val char = _characteristics.value[i]
                for (j in char.skills.indices) {
                    val skill = char.skills[j]
                    indexMap[skill.type] = Pair(i, j)
                }
            }
            reverseIndex = indexMap
        }
    }

    override fun onRoll(skillType: Skill.Type) {
        val index = reverseIndex[skillType]
        val characteristicIndex = index!!.first
        val skillIndex = index.second
        val skill = _characteristics.value[characteristicIndex].skills[skillIndex]
        listener.onRollEvent(
            EventRoll.fromSkillType(
                EventRoll.Type.CHECK, skillType, skill.modifier + skill.level * 3
            )
        )
    }

    override fun onSelect(skillType: Skill.Type) {
        val newList = mutableListOf<Characteristic>()
        val index = reverseIndex[skillType]
        val characteristicIndex = index!!.first
        val skillIndex = index.second

        for (i in _characteristics.value.indices) {
            if (i == characteristicIndex) {
                val characteristic = _characteristics.value[i]
                val newSkills = characteristic.skills.toMutableList()
                val oldSkill = newSkills[skillIndex]
                newSkills[skillIndex] = oldSkill.copy(
                    level = (oldSkill.level + 1) % 3
                )
                newList.add(characteristic.copy(skills = newSkills))
            } else {
                newList.add(_characteristics.value[i])
            }
        }
        _characteristics.value = newList
        updateRecord()
    }

    override fun onCharacteristicRoll(type: Characteristic.Type) {
        val characteristic = _characteristics.value.find { it.type == type }

        characteristic?.let {
            listener.onRollEvent(
                EventRoll.fromCharacteristicType(
                    EventRoll.Type.CHECK,
                    characteristic.type,
                    DndUtilities.getCharacteristicsModifier(characteristic.value)
                )
            )
        }
    }

    override fun onCharacteristicSaveThrow(type: Characteristic.Type) {
        val characteristic = _characteristics.value.find { it.type == type }

        characteristic?.let {
            val modifier = if (characteristic.isSaveThrowChecked) {
                DndUtilities.getCharacteristicsModifier(characteristic.value) + 3
            } else {
                DndUtilities.getCharacteristicsModifier(characteristic.value)
            }
            listener.onRollEvent(
                EventRoll.fromCharacteristicType(
                    EventRoll.Type.SAVE_THROW,
                    characteristic.type,
                    modifier
                )
            )
        }
    }

    override fun onSaveThrowCheckChanged(type: Characteristic.Type) {
        val newList = mutableListOf<Characteristic>()
        for (char in _characteristics.value) {
            if (char.type == type) {
                newList.add(
                    char.copy(
                        isSaveThrowChecked = !char.isSaveThrowChecked
                    )
                )
            } else {
                newList.add(char)
            }
        }
        _characteristics.value = newList
        updateRecord()
    }

    private fun updateRecord() = viewModelScope.launch {
        val entity = Converter.characteristicsToEntity(characterId, _characteristics.value)
        databaseService.updateCharacteristicsAndSkills(entity)
    }
}