package ru.kima.dndcharactersheet.data.entities

data class CharacterEntity(
    val id: Int = 0,
    val name: String = String(),
    val race: String = String(),
    val charClass: String = String(),
    val currentHp: Int = 0,
    val maxHp: Int = 0
)
