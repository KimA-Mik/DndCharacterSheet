package ru.kima.dndcharactersheet.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import ru.kima.dndcharactersheet.App
import ru.kima.dndcharactersheet.ui.sheet.CharacterSheetFragment
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.CharacteristicsAndSkillsViewModel

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            CharacteristicsAndSkillsViewModel::class.java -> {
                val fragment = app.activity.fragmentContainerView
                    .getFragment<NavHostFragment>().childFragmentManager
                    .fragments[0] as CharacterSheetFragment
                val listener = fragment.getCharacteristicsAndAbilitiesListener()
                CharacteristicsAndSkillsViewModel(listener)
            }

            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)