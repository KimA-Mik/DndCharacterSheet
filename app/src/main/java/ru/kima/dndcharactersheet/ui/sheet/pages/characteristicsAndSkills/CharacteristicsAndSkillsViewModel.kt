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
import ru.kima.dndcharactersheet.ui.sheet.event.RollType
import ru.kima.dndcharactersheet.ui.sheet.event.RollValue
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Characteristic
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillType
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
                Skill(SkillType.ATHLETICS, dndUtilities.getCharacteristicsModifier(19), 1),
                Skill(SkillType.ACROBATICS, dndUtilities.getCharacteristicsModifier(19), 2)
            )
        ),
        Characteristic(Characteristic.Type.CONSTITUTION, 15, emptyList()),
        Characteristic(Characteristic.Type.DEXTERITY, 20, emptyList()),
        Characteristic(Characteristic.Type.INTELLIGENCE, 8, emptyList()),
        Characteristic(Characteristic.Type.WISDOM, 3, emptyList()),
        Characteristic(Characteristic.Type.CHARISMA, 0, emptyList())
    )

    private val _characteristics = MutableStateFlow(characteristicsList)
    val characteristic = _characteristics.asStateFlow()

    private val reverseIndex: Map<SkillType, Pair<Int, Int>>

    init {
        val indexMap = mutableMapOf<SkillType, Pair<Int, Int>>()
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
        listener.onRoll(RollType.CHECK, RollValue.STRENGTH)
    }

    override fun onRoll(skillType: SkillType) {
        val index = reverseIndex[skillType]
        val characteristicIndex = index!!.first
        val skillIndex = index.second
        val skill = _characteristics.value[characteristicIndex].skills[skillIndex]
        listener.onRollEvent(
            EventRoll.fromSkillType(
                RollType.CHECK, skillType, skill.modifier + skill.level * 3
            )
        )
    }

    //TODO: Fix visible redraw of all skills
    override fun onSelect(skillType: SkillType) {
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