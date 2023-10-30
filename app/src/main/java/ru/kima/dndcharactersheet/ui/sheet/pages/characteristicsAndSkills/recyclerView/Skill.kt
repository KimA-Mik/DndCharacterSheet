package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillType

data class Skill(
    val type: SkillType,
    val modifier: Int,
    val level: Int
)