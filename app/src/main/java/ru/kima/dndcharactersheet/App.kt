package ru.kima.dndcharactersheet

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.kima.dndcharactersheet.model.CharactersDatabaseService

class App : Application() {
    private var _activity: MainActivity? = null
    val activity
        get() = checkNotNull(_activity) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(module {
                singleOf(::CharactersDatabaseService)
            })
        }
    }

    fun setActivity(activity: MainActivity) {
        _activity = activity
    }

    fun freeActivity() {
        _activity = null
    }
}