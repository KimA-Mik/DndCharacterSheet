package ru.kima.dndcharactersheet.model

import android.content.Context
import androidx.room.Room
import ru.kima.dndcharactersheet.data.database.CharactersDatabase
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.data.entities.CharacteristicsAndSkillsEntity


private const val DATABASE_NAME = "characters-database"

class CharactersDatabaseService(
    context: Context
) {
    private val charactersDatabase: CharactersDatabase = Room
        .databaseBuilder(
            context,
            CharactersDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    suspend fun getAllCharacters(): List<CharacterEntity> =
        charactersDatabase.charactersDao().getAllCharacters()

    suspend fun getCharacterById(id: Int): CharacterEntity? =
        charactersDatabase.charactersDao().getCharacterById(id)

    suspend fun addCharacter(character: CharacterEntity) =
        charactersDatabase.charactersDao().addCharacter(character)

    suspend fun updateCharacter(character: CharacterEntity) =
        charactersDatabase.charactersDao().updateCharacter(character)

    suspend fun deleteCharacterById(id: Int) {
        charactersDatabase.charactersDao().deleteCharacterById(id)
        charactersDatabase.characteristicsAndSkillsDao().delete(id)
    }

    suspend fun getCharacteristicsAndSkills(id: Int): CharacteristicsAndSkillsEntity {
        val existed = charactersDatabase.characteristicsAndSkillsDao().getById(id)
        if (existed != null) {
            return existed
        }

        val fresh = CharacteristicsAndSkillsEntity(characterId = id)
        charactersDatabase.characteristicsAndSkillsDao().add(fresh)
        return fresh
    }

    suspend fun updateCharacteristicsAndSkills(entity: CharacteristicsAndSkillsEntity) =
        charactersDatabase.characteristicsAndSkillsDao().update(entity)
}