package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills

interface SkillViewListener {
    fun onRoll(skillType: SkillType)
    fun onSelect(skillType: SkillType)
}