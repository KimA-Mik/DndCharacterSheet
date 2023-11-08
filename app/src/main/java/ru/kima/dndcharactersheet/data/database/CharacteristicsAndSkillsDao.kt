package ru.kima.dndcharactersheet.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.kima.dndcharactersheet.data.entities.CharacteristicsAndSkillsEntity

@Dao
interface CharacteristicsAndSkillsDao {
    @Query("SELECT * FROM $CHARACTERISTICS_AND_SKILLS_DB_NAME WHERE characterId = :characterId")
    suspend fun getById(characterId: Int): CharacteristicsAndSkillsEntity?

    @Insert
    suspend fun add(entity: CharacteristicsAndSkillsEntity)

    @Update
    suspend fun update(entity: CharacteristicsAndSkillsEntity)

    @Query("DELETE FROM $CHARACTERISTICS_AND_SKILLS_DB_NAME WHERE characterId = :characterId")
    suspend fun delete(characterId: Int)
}