package ru.kima.dndcharactersheet.ui.sheet.event

import ru.kima.dndcharactersheet.R

enum class RollValue(val strId: Int) {
    NONE(R.string.empty),

    //CHARACTERISTICS
    STRENGTH(R.string.check_value_strength),
    AGILITY(R.string.check_value_agility),

    //SKILLS
    ATHLETICS(R.string.check_skill_athletics),
    ACROBATICS(R.string.check_skill_acrobatics)
}
