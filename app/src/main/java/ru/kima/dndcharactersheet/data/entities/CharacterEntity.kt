package ru.kima.dndcharactersheet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kima.dndcharactersheet.data.database.CHARACTERS_DB_NAME

@Entity(tableName = CHARACTERS_DB_NAME)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = String(),
    val race: String = String(),
    val charClass: String = String(),
    val currentHp: Int = 0,
    val maxHp: Int = 0,
    val armorClass: Int = 0,
    val experiencePoints: Int = 0
)
//Модификатор характеристики: (Значение характеристики - 10) / 2. Округление в меньшую сторону.