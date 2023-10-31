package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

import ru.kima.dndcharactersheet.R

data class Characteristic(
    val type: Type,
    val value: Int,
    val isSaveThrowChecked: Boolean,
    val skills: List<Skill>
) {
    enum class Type(val titleId: Int) {
        STRENGTH(R.string.characteristic_strength),
        CONSTITUTION(R.string.characteristic_constitution),
        DEXTERITY(R.string.characteristic_dexterity),
        INTELLIGENCE(R.string.characteristic_intelligence),
        WISDOM(R.string.characteristic_wisdom),
        CHARISMA(R.string.characteristic_charisma)
    }
}
