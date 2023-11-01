package ru.kima.dndcharactersheet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.data.entities.CharacteristicsAndSkillsEntity

@Database(
    entities = [
        CharacterEntity::class,
        CharacteristicsAndSkillsEntity::class
    ],
    version = 1
)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun characteristicsAndSkillsDao(): CharacteristicsAndSkillsDao
}