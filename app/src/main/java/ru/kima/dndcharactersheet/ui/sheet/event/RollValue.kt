package ru.kima.dndcharactersheet.ui.sheet.event

import ru.kima.dndcharactersheet.R

enum class RollValue(val strId: Int) {
    NONE(R.string.empty),
    STRENGTH(R.string.check_value_strength),
    AGILITY(R.string.check_value_agility)
}
