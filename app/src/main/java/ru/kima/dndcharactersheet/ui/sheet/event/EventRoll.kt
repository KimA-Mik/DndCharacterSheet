package ru.kima.dndcharactersheet.ui.sheet.event

import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillType

data class EventRoll(
    val type: RollType = RollType.NONE,
    val value: RollValue = RollValue.NONE,
    val modifier: Int = 0
) {
    companion object {
        fun fromSkillType(rollType: RollType, skillType: SkillType, modifier: Int): EventRoll {
            return EventRoll(
                type = rollType,
                value = skillToEvent[skillType]!!,
                modifier
            )
        }

        fun valueFromSkillType(skillType: SkillType): RollValue {
            return skillToEvent[skillType]!!
        }

        private val skillToEvent = mapOf(
            SkillType.ATHLETICS to RollValue.ATHLETICS,
            SkillType.ACROBATICS to RollValue.ACROBATICS
        )
    }
}
