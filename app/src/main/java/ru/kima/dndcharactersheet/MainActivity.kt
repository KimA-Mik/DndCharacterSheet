package ru.kima.dndcharactersheet

import android.app.UiModeManager
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.fragment.app.FragmentContainerView
import androidx.preference.PreferenceManager
import ru.kima.dndcharactersheet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var app: App
    lateinit var fragmentContainerView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        app = applicationContext as App
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentContainerView = binding.fragmentContainer
        app.setActivity(this)

        applyPreferences()

        //TODO: explore elevated colors dependency with xml colors
//        val color = SurfaceColors.SURFACE_2.getColor(this)
//        window.statusBarColor = color
//        window.navigationBarColor = color
    }

    override fun onDestroy() {
        super.onDestroy()
        app.freeActivity()
    }

    private fun applyPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        val pref = PreferenceManager.getDefaultSharedPreferences(this).all
        val darkThemeKey = getString(R.string.dark_theme_preference_key)
        val darkTheme = pref[darkThemeKey].toString().toInt()

        if (SDK_INT < 31) {
            when (darkTheme) {
                -1 -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                0 -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                1 -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        } else {
            val modeManager: UiModeManager =
                getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            when (darkTheme) {
                -1 -> modeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_NO)
                0 -> modeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_AUTO)
                1 -> modeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
            }
        }
    }
}