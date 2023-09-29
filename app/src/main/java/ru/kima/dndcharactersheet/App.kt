package ru.kima.dndcharactersheet

import android.app.Application
import ru.kima.dndcharactersheet.model.CharactersDatabaseService

class App : Application() {
    val databaseService by lazy { CharactersDatabaseService(applicationContext) }

    private var _activity: MainActivity? = null
    val activity
        get() = checkNotNull(_activity) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    fun setActivity(activity: MainActivity) {
        _activity = activity
    }

    fun freeActivity() {
        _activity = null
    }
}