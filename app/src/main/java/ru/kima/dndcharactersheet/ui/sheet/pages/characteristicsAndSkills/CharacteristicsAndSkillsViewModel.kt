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
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillViewListener
import ru.kima.dndcharactersheet.ui.sheet.pages.listeners.CharacteristicsAndAbilitiesListener

class CharacteristicsAndSkillsViewModel(
    private val listener: CharacteristicsAndAbilitiesListener
) : ViewModel(), KoinComponent, SkillViewListener {
    private val dndUtilities = DndUtilities()
    private val databaseService: CharactersDatabaseService by inject()

    private val characteristicsList = listOf(
        Characteristic(
            Characteristic.Type.STRENGTH, 19, listOf(
                Skill(Skill.Type.ATHLETICS, dndUtilities.getCharacteristicsModifier(19), 1)
            )
        ),
        Characteristic(
            Characteristic.Type.DEXTERITY, 20, listOf(
                Skill(Skill.Type.ACROBATICS, dndUtilities.getCharacteristicsModifier(20), 2),
                Skill(Skill.Type.SLEIGHT_OF_HAND, dndUtilities.getCharacteristicsModifier(20), 0),
                Skill(Skill.Type.STEALTH, dndUtilities.getCharacteristicsModifier(20), 0),
            )
        ),
        Characteristic(Characteristic.Type.CONSTITUTION, 15, emptyList()),
        Characteristic(
            Characteristic.Type.INTELLIGENCE, 8, listOf(
                Skill(Skill.Type.ARCANA, dndUtilities.getCharacteristicsModifier(8), 0),
                Skill(Skill.Type.HISTORY, dndUtilities.getCharacteristicsModifier(8), 0),
                Skill(Skill.Type.INVESTIGATION, dndUtilities.getCharacteristicsModifier(8), 0),
                Skill(Skill.Type.NATURE, dndUtilities.getCharacteristicsModifier(8), 0),
                Skill(Skill.Type.RELIGION, dndUtilities.getCharacteristicsModifier(8), 0),
            )
        ),
        Characteristic(
            Characteristic.Type.WISDOM, 3, listOf(
                Skill(Skill.Type.ANIMAL_HANDLING, dndUtilities.getCharacteristicsModifier(3), 0),
                Skill(Skill.Type.INSIGHT, dndUtilities.getCharacteristicsModifier(3), 0),
                Skill(Skill.Type.MEDICINE, dndUtilities.getCharacteristicsModifier(3), 0),
                Skill(Skill.Type.PERCEPTION, dndUtilities.getCharacteristicsModifier(3), 0),
                Skill(Skill.Type.SURVIVAL, dndUtilities.getCharacteristicsModifier(3), 0),
            )
        ),
        Characteristic(
            Characteristic.Type.CHARISMA, 0, listOf(
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

    fun roll() = viewModelScope.launch {
        listener.onRoll(EventRoll.Type.CHECK, EventRoll.Value.STRENGTH)
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
}