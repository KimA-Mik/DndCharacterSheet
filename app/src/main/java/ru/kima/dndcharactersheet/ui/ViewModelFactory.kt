package ru.kima.dndcharactersheet.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kima.dndcharactersheet.App
import ru.kima.dndcharactersheet.ui.characterslist.CharacterListViewModel
import ru.kima.dndcharactersheet.ui.sheet.CharacterSheetViewModel

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            CharacterListViewModel::class.java -> {
                CharacterListViewModel(app.databaseService)
            }

            CharacterSheetViewModel::class.java -> {
                CharacterSheetViewModel(app.databaseService)
            }

            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)