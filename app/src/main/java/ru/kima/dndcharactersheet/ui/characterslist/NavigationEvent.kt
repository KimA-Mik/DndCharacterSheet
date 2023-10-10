package ru.kima.dndcharactersheet.ui.characterslist

sealed class NavigationEvent(val data: Int) {
    class Settings : NavigationEvent(0)
    class ShowSheet(val id: Int) : NavigationEvent(id)
}