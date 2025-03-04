package ru.kima.dndcharactersheet.ui.editCharacter.info

interface InfoUpdateListener {
    fun onInfoUpdated(type: InfoType, info: String)
}