package ru.kima.dndcharactersheet

import android.app.Application
import ru.kima.dndcharactersheet.model.CharactersDatabaseService

class App : Application() {
    val databaseService by lazy { CharactersDatabaseService(applicationContext) }
}