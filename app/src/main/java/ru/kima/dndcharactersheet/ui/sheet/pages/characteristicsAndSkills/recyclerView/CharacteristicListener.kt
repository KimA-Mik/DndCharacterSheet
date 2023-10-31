package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

interface CharacteristicListener {
    fun onCharacteristicRoll(type: Characteristic.Type)
    fun onCharacteristicSaveThrow(type: Characteristic.Type)
    fun onSaveThrowCheckChanged(type: Characteristic.Type)
}