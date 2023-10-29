package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

data class Characteristic(
    val title: Int,
    val value: Int,
    val skills: List<Skill>
)
