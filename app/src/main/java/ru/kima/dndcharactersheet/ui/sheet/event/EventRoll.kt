package ru.kima.dndcharactersheet.ui.sheet.event

import ru.kima.dndcharactersheet.R
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill

data class EventRoll(
    val type: Type = Type.NONE,
    val value: Value = Value.NONE,
    val modifier: Int = 0
) {
    enum class Type(val strId: Int) {
        NONE(R.string.roll_type_roll),
        CHECK(R.string.roll_type_check)
    }

    enum class Value(val strId: Int) {
        NONE(R.string.empty),

        //CHARACTERISTICS
        STRENGTH(R.string.check_value_strength),
        AGILITY(R.string.check_value_agility),

        //SKILLS
        ATHLETICS(R.string.check_skill_athletics),
        ACROBATICS(R.string.check_skill_acrobatics),
        SLEIGHT_OF_HAND(R.string.check_skill_sleight_of_hand),
        STEALTH(R.string.check_skill_stealth),
        ARCANA(R.string.check_skill_arcana),
        HISTORY(R.string.check_skill_history),
        INVESTIGATION(R.string.check_skill_investigation),
        NATURE(R.string.check_skill_nature),
        RELIGION(R.string.check_skill_religion),
        ANIMAL_HANDLING(R.string.check_skill_animal_handling),
        INSIGHT(R.string.check_skill_insight),
        MEDICINE(R.string.check_skill_medicine),
        PERCEPTION(R.string.check_skill_perception),
        SURVIVAL(R.string.check_skill_survival),
        DECEPTION(R.string.check_skill_deception),
        INTIMIDATION(R.string.check_skill_intimidation),
        PERFORMANCE(R.string.check_skill_performance),
        PERSUASION(R.string.check_skill_persuasion)
    }

    companion object {
        fun fromSkillType(rollType: Type, skillType: Skill.Type, modifier: Int): EventRoll {
            return EventRoll(
                type = rollType,
                value = skillToEvent[skillType]!!,
                modifier
            )
        }

        fun valueFromSkillType(skillType: Skill.Type): Value {
            return skillToEvent[skillType]!!
        }

        private val skillToEvent = mapOf(
            Skill.Type.ATHLETICS to Value.ATHLETICS,
            Skill.Type.ACROBATICS to Value.ACROBATICS,
            Skill.Type.SLEIGHT_OF_HAND to Value.SLEIGHT_OF_HAND,
            Skill.Type.STEALTH to Value.STEALTH,
            Skill.Type.ARCANA to Value.ARCANA,
            Skill.Type.HISTORY to Value.HISTORY,
            Skill.Type.INVESTIGATION to Value.INVESTIGATION,
            Skill.Type.NATURE to Value.NATURE,
            Skill.Type.RELIGION to Value.RELIGION,
            Skill.Type.ANIMAL_HANDLING to Value.ANIMAL_HANDLING,
            Skill.Type.INSIGHT to Value.INSIGHT,
            Skill.Type.MEDICINE to Value.MEDICINE,
            Skill.Type.PERCEPTION to Value.PERCEPTION,
            Skill.Type.SURVIVAL to Value.SURVIVAL,
            Skill.Type.DECEPTION to Value.DECEPTION,
            Skill.Type.INTIMIDATION to Value.INTIMIDATION,
            Skill.Type.PERFORMANCE to Value.PERFORMANCE,
            Skill.Type.PERSUASION to Value.PERSUASION,
        )
    }
}
