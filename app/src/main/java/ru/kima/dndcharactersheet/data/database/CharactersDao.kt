package ru.kima.dndcharactersheet.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.kima.dndcharactersheet.data.entities.CharacterEntity

@Dao
interface CharactersDao {
    @Query("SELECT * FROM $CHARACTERS_DB_NAME WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("SELECT * FROM $CHARACTERS_DB_NAME")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Insert
    suspend fun addCharacter(character: CharacterEntity)

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Query("DELETE FROM $CHARACTERS_DB_NAME WHERE id = :id")
    suspend fun deleteCharacterById(id: Int)
}