package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

import ru.kima.dndcharactersheet.data.entities.CharacteristicsAndSkillsEntity
import ru.kima.dndcharactersheet.dnd.DndUtilities

object Converter {
    fun entityToCharacteristics(e: CharacteristicsAndSkillsEntity): List<Characteristic> {
        val dexterityModifier = DndUtilities.getCharacteristicsModifier(e.dexterityValue)
        val intelligenceModifier = DndUtilities.getCharacteristicsModifier(e.intelligenceValue)
        val wisdomModifier = DndUtilities.getCharacteristicsModifier(e.wisdomValue)
        val charismaModifier = DndUtilities.getCharacteristicsModifier(e.charismaValue)

        return listOf(
            Characteristic(
                Characteristic.Type.STRENGTH,
                e.strengthValue,
                e.strengthChecked,
                listOf(
                    Skill(
                        Skill.Type.ATHLETICS,
                        DndUtilities.getCharacteristicsModifier(e.strengthValue),
                        e.athleticsLevel
                    )
                )
            ),
            Characteristic(
                Characteristic.Type.DEXTERITY,
                e.dexterityValue,
                e.dexterityChecked, listOf(
                    Skill(Skill.Type.ACROBATICS, dexterityModifier, e.acrobaticsLevel),
                    Skill(Skill.Type.SLEIGHT_OF_HAND, dexterityModifier, e.sleightOfHandLevel),
                    Skill(Skill.Type.STEALTH, dexterityModifier, e.stealthLevel),
                )
            ),
            Characteristic(
                Characteristic.Type.CONSTITUTION,
                e.constitutionValue,
                e.constitutionChecked,
                emptyList()
            ),
            Characteristic(
                Characteristic.Type.INTELLIGENCE,
                e.intelligenceValue,
                e.intelligenceChecked,
                listOf(
                    Skill(Skill.Type.ARCANA, intelligenceModifier, e.arcanaLevel),
                    Skill(Skill.Type.HISTORY, intelligenceModifier, e.historyLevel),
                    Skill(Skill.Type.INVESTIGATION, intelligenceModifier, e.investigationLevel),
                    Skill(Skill.Type.NATURE, intelligenceModifier, e.natureLevel),
                    Skill(Skill.Type.RELIGION, intelligenceModifier, e.religionLevel),
                )
            ),
            Characteristic(
                Characteristic.Type.WISDOM,
                e.wisdomValue,
                e.wisdomChecked,
                listOf(
                    Skill(Skill.Type.ANIMAL_HANDLING, wisdomModifier, e.animalHandlingLevel),
                    Skill(Skill.Type.INSIGHT, wisdomModifier, e.insightLevel),
                    Skill(Skill.Type.MEDICINE, wisdomModifier, e.medicineLevel),
                    Skill(Skill.Type.PERCEPTION, wisdomModifier, e.perceptionLevel),
                    Skill(Skill.Type.SURVIVAL, wisdomModifier, e.survivalLevel),
                )
            ),
            Characteristic(
                Characteristic.Type.CHARISMA,
                e.charismaValue,
                e.charismaChecked,
                listOf(
                    Skill(Skill.Type.DECEPTION, charismaModifier, e.deceptionLevel),
                    Skill(Skill.Type.INTIMIDATION, charismaModifier, e.intimidationLevel),
                    Skill(Skill.Type.PERFORMANCE, charismaModifier, e.performanceLevel),
                    Skill(Skill.Type.PERSUASION, charismaModifier, e.persuasionLevel),
                )
            )
        )
    }

    fun characteristicsToEntity(
        id: Int,
        characteristics: List<Characteristic>
    ): CharacteristicsAndSkillsEntity {
        //Characteristics
        var strengthValue = 10
        var strengthChecked = false
        var constitutionValue = 10
        var constitutionChecked = false
        var dexterityValue = 10
        var dexterityChecked = false
        var intelligenceValue = 10
        var intelligenceChecked = false
        var wisdomValue = 10
        var wisdomChecked = false
        var charismaValue = 10
        var charismaChecked = false

        //Skills
        var athleticsLevel = 0
        var acrobaticsLevel = 0
        var sleightOfHandLevel = 0
        var stealthLevel = 0
        var arcanaLevel = 0
        var historyLevel = 0
        var investigationLevel = 0
        var natureLevel = 0
        var religionLevel = 0
        var animalHandlingLevel = 0
        var insightLevel = 0
        var medicineLevel = 0
        var perceptionLevel = 0
        var survivalLevel = 0
        var deceptionLevel = 0
        var intimidationLevel = 0
        var performanceLevel = 0
        var persuasionLevel = 0

        for (char in characteristics) {
            when (char.type) {
                Characteristic.Type.STRENGTH -> {
                    strengthValue = char.value
                    strengthChecked = char.isSaveThrowChecked
                }

                Characteristic.Type.CONSTITUTION -> {
                    constitutionValue = char.value
                    constitutionChecked = char.isSaveThrowChecked
                }

                Characteristic.Type.DEXTERITY -> {
                    dexterityValue = char.value
                    dexterityChecked = char.isSaveThrowChecked
                }

                Characteristic.Type.INTELLIGENCE -> {
                    intelligenceValue = char.value
                    intelligenceChecked = char.isSaveThrowChecked
                }

                Characteristic.Type.WISDOM -> {
                    wisdomValue = char.value
                    wisdomChecked = char.isSaveThrowChecked
                }

                Characteristic.Type.CHARISMA -> {
                    charismaValue = char.value
                    charismaChecked = char.isSaveThrowChecked
                }
            }

            for (skill in char.skills) {
                when (skill.type) {
                    Skill.Type.ATHLETICS -> athleticsLevel = skill.level
                    Skill.Type.ACROBATICS -> acrobaticsLevel = skill.level
                    Skill.Type.SLEIGHT_OF_HAND -> sleightOfHandLevel = skill.level
                    Skill.Type.STEALTH -> stealthLevel = skill.level
                    Skill.Type.ARCANA -> arcanaLevel = skill.level
                    Skill.Type.HISTORY -> historyLevel = skill.level
                    Skill.Type.INVESTIGATION -> investigationLevel = skill.level
                    Skill.Type.NATURE -> natureLevel = skill.level
                    Skill.Type.RELIGION -> religionLevel = skill.level
                    Skill.Type.ANIMAL_HANDLING -> animalHandlingLevel = skill.level
                    Skill.Type.INSIGHT -> insightLevel = skill.level
                    Skill.Type.MEDICINE -> medicineLevel = skill.level
                    Skill.Type.PERCEPTION -> perceptionLevel = skill.level
                    Skill.Type.SURVIVAL -> survivalLevel = skill.level
                    Skill.Type.DECEPTION -> deceptionLevel = skill.level
                    Skill.Type.INTIMIDATION -> intimidationLevel = skill.level
                    Skill.Type.PERFORMANCE -> performanceLevel = skill.level
                    Skill.Type.PERSUASION -> persuasionLevel = skill.level
                }
            }
        }

        return CharacteristicsAndSkillsEntity(
            id,
            //Characteristics
            strengthValue,
            strengthChecked,
            constitutionValue,
            constitutionChecked,
            dexterityValue,
            dexterityChecked,
            intelligenceValue,
            intelligenceChecked,
            wisdomValue,
            wisdomChecked,
            charismaValue,
            charismaChecked,

            //Skills
            athleticsLevel,
            acrobaticsLevel,
            sleightOfHandLevel,
            stealthLevel,
            arcanaLevel,
            historyLevel,
            investigationLevel,
            natureLevel,
            religionLevel,
            animalHandlingLevel,
            insightLevel,
            medicineLevel,
            perceptionLevel,
            survivalLevel,
            deceptionLevel,
            intimidationLevel,
            performanceLevel,
            persuasionLevel,
        )
    }

    val defaultCharacteristics = listOf(
        Characteristic(
            Characteristic.Type.STRENGTH,
            10,
            false,
            listOf(
                Skill(
                    Skill.Type.ATHLETICS,
                    DndUtilities.getCharacteristicsModifier(10),
                    0
                )
            )
        ),
        Characteristic(
            Characteristic.Type.DEXTERITY, 0, false, listOf(
                Skill(Skill.Type.ACROBATICS, 0, 0),
                Skill(Skill.Type.SLEIGHT_OF_HAND, 0, 0),
                Skill(Skill.Type.STEALTH, 0, 0),
            )
        ),
        Characteristic(
            Characteristic.Type.CONSTITUTION,
            10,
            false,
            emptyList()
        ),
        Characteristic(
            Characteristic.Type.INTELLIGENCE,
            10,
            false,
            listOf(
                Skill(Skill.Type.ARCANA, 0, 0),
                Skill(Skill.Type.HISTORY, 0, 0),
                Skill(Skill.Type.INVESTIGATION, 0, 0),
                Skill(Skill.Type.NATURE, 0, 0),
                Skill(Skill.Type.RELIGION, 0, 0),
            )
        ),
        Characteristic(
            Characteristic.Type.WISDOM,
            10,
            false,
            listOf(
                Skill(Skill.Type.ANIMAL_HANDLING, 0, 0),
                Skill(Skill.Type.INSIGHT, 0, 0),
                Skill(Skill.Type.MEDICINE, 0, 0),
                Skill(Skill.Type.PERCEPTION, 0, 0),
                Skill(Skill.Type.SURVIVAL, 0, 0),
            )
        ),
        Characteristic(
            Characteristic.Type.CHARISMA,
            10,
            false,
            listOf(
                Skill(Skill.Type.DECEPTION, 0, 0),
                Skill(Skill.Type.INTIMIDATION, 0, 0),
                Skill(Skill.Type.PERFORMANCE, 0, 0),
                Skill(Skill.Type.PERSUASION, 0, 0),
            )
        )
    )
}