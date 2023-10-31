package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills

import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill

interface SkillViewListener {
    fun onRoll(skillType: Skill.Type)
    fun onSelect(skillType: Skill.Type)
}