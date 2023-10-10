package ru.kima.dndcharactersheet.ui.settings.preferences

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import ru.kima.dndcharactersheet.R

class PreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        findPreference<ListPreference>(getString(R.string.dark_theme_preference_key))
            ?.setOnPreferenceChangeListener { _, _ ->
                requireActivity().recreate()
                true
            }
    }
}