package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

import ru.kima.dndcharactersheet.R

data class Skill(
    val type: Type,
    val modifier: Int,
    val level: Int
) {
    enum class Type(val titleId: Int) {
        ATHLETICS(R.string.skill_athletics),
        ACROBATICS(R.string.skill_acrobatics),
        SLEIGHT_OF_HAND(R.string.skill_sleight_of_hand),
        STEALTH(R.string.skill_stealth),
        ARCANA(R.string.skill_arcana),
        HISTORY(R.string.skill_history),
        INVESTIGATION(R.string.skill_investigation),
        NATURE(R.string.skill_nature),
        RELIGION(R.string.skill_religion),
        ANIMAL_HANDLING(R.string.skill_animal_handling),
        INSIGHT(R.string.skill_insight),
        MEDICINE(R.string.skill_medicine),
        PERCEPTION(R.string.skill_perception),
        SURVIVAL(R.string.skill_survival),
        DECEPTION(R.string.skill_deception),
        INTIMIDATION(R.string.skill_intimidation),
        PERFORMANCE(R.string.skill_performance),
        PERSUASION(R.string.skill_persuasion),
    }
}