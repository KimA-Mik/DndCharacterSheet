package ru.kima.dndcharactersheet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kima.dndcharactersheet.data.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}