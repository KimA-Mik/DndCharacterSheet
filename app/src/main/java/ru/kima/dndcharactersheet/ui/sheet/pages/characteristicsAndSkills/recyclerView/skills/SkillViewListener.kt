package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills

interface SkillViewListener {
    fun onRoll(skill: SkillType)
    fun onCheck(skill: SkillType)
}