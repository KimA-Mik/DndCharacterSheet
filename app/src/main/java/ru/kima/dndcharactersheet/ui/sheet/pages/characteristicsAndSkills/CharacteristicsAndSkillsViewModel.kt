package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.kima.dndcharactersheet.dnd.DndUtilities
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.ui.sheet.event.EventRoll
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Characteristic
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.CharacteristicListener
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillViewListener
import ru.kima.dndcharactersheet.ui.sheet.pages.listeners.CharacteristicsAndAbilitiesListener

class CharacteristicsAndSkillsViewModel(
    private val listener: CharacteristicsAndAbilitiesListener
) : ViewModel(), KoinComponent, SkillViewListener, CharacteristicListener {
    private val dndUtilities = DndUtilities()
    private val databaseService: CharactersDatabaseService by inject()

    private val characteristicsList = listOf(
        Characteristic(
            Characteristic.Type.STRENGTH, 19, true, listOf(
                Skill(Skill.Type.ATHLETICS, dndUtilities.getCharacteristicsModifier(19), 1)
            )
        ),
        Characteristic(
            Characteristic.Type.DEXTERITY, 20, false, listOf(
                Skill(Skill.Type.ACROBATICS, dndUtilities.getCharacteristicsModifier(20), 2),
                Skill(Skill.Type.SLEIGHT_OF_HAND, dndUtilities.getCharacteristicsModifier(20), 0),
                Skill(Skill.Type.STEALTH, dndUtilities.getCharacteristicsModifier(20), 0),
            )
        ),
        Characteristic(Characteristic.Type.CONSTITUTION, 15, false, emptyList()),
        Characteristic(
            Characteristic.Type.INTELLIGENCE, 8, false, listOf(
                Skill(Skill.Type.ARCANA, dndUtilities.getCharacteristicsModifier(8), 0),
                Skill(Skill.Type.HISTORY, dndUtilities.getCharacteristicsModifier(8), 0),
                Skill(Skill.Type.INVESTIGATION, dndUtilities.getCharacteristicsModifier(8), 0),
                Skill(Skill.Type.NATURE, dndUtilities.getCharacteristicsModifier(8), 0),
                Skill(Skill.Type.RELIGION, dndUtilities.getCharacteristicsModifier(8), 0),
            )
        ),
        Characteristic(
            Characteristic.Type.WISDOM, 3, false, listOf(
                Skill(Skill.Type.ANIMAL_HANDLING, dndUtilities.getCharacteristicsModifier(3), 0),
                Skill(Skill.Type.INSIGHT, dndUtilities.getCharacteristicsModifier(3), 0),
                Skill(Skill.Type.MEDICINE, dndUtilities.getCharacteristicsModifier(3), 0),
                Skill(Skill.Type.PERCEPTION, dndUtilities.getCharacteristicsModifier(3), 0),
                Skill(Skill.Type.SURVIVAL, dndUtilities.getCharacteristicsModifier(3), 0),
            )
        ),
        Characteristic(
            Characteristic.Type.CHARISMA, 0, false, listOf(
                Skill(Skill.Type.DECEPTION, dndUtilities.getCharacteristicsModifier(0), 0),
                Skill(Skill.Type.INTIMIDATION, dndUtilities.getCharacteristicsModifier(0), 0),
                Skill(Skill.Type.PERFORMANCE, dndUtilities.getCharacteristicsModifier(0), 0),
                Skill(Skill.Type.PERSUASION, dndUtilities.getCharacteristicsModifier(0), 0),
            )
        )
    )

    private val _characteristics = MutableStateFlow(characteristicsList)
    val characteristic = _characteristics.asStateFlow()

    private val reverseIndex: Map<Skill.Type, Pair<Int, Int>>

    init {
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
    }

    override fun onCharacteristicRoll(type: Characteristic.Type) {
        val characteristic = _characteristics.value.find { it.type == type }

        characteristic?.let {
            listener.onRollEvent(
                EventRoll.fromCharacteristicType(
                    EventRoll.Type.CHECK,
                    characteristic.type,
                    dndUtilities.getCharacteristicsModifier(characteristic.value)
                )
            )
        }
    }

    override fun onCharacteristicSaveThrow(type: Characteristic.Type) {
        val characteristic = _characteristics.value.find { it.type == type }

        characteristic?.let {
            val modifier = if (characteristic.isSaveThrowChecked) {
                dndUtilities.getCharacteristicsModifier(characteristic.value) + 3
            } else {
                dndUtilities.getCharacteristicsModifier(characteristic.value)
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
    }
}