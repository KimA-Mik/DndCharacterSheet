package ru.kima.dndcharactersheet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kima.dndcharactersheet.data.database.CHARACTERISTICS_AND_SKILLS_DB_NAME

@Entity(tableName = CHARACTERISTICS_AND_SKILLS_DB_NAME)
data class CharacteristicsAndSkillsEntity(
    @PrimaryKey
    val characterId: Int,

    //Characteristics
    val strengthValue: Int = 10,
    val strengthChecked: Boolean = false,
    val constitutionValue: Int = 10,
    val constitutionChecked: Boolean = false,
    val dexterityValue: Int = 10,
    val dexterityChecked: Boolean = false,
    val intelligenceValue: Int = 10,
    val intelligenceChecked: Boolean = false,
    val wisdomValue: Int = 10,
    val wisdomChecked: Boolean = false,
    val charismaValue: Int = 10,
    val charismaChecked: Boolean = false,

    //Skills
    val athleticsLevel: Int = 0,
    val acrobaticsLevel: Int = 0,
    val sleightOfHandLevel: Int = 0,
    val stealthLevel: Int = 0,
    val arcanaLevel: Int = 0,
    val historyLevel: Int = 0,
    val investigationLevel: Int = 0,
    val natureLevel: Int = 0,
    val religionLevel: Int = 0,
    val animalHandlingLevel: Int = 0,
    val insightLevel: Int = 0,
    val medicineLevel: Int = 0,
    val perceptionLevel: Int = 0,
    val survivalLevel: Int = 0,
    val deceptionLevel: Int = 0,
    val intimidationLevel: Int = 0,
    val performanceLevel: Int = 0,
    val persuasionLevel: Int = 0
)