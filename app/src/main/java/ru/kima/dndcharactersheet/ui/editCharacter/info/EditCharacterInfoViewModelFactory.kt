package ru.kima.dndcharactersheet.ui.editCharacter.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class EditCharacterInfoViewModelFactory(private val characterId: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        // Get the Application object from extras
//        val application =
//            checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
        // Create a SavedStateHandle for this ViewModel from extras
//        val savedStateHandle = extras.createSavedStateHandle()

        return EditCharacterInfoViewModel(
            characterId
        ) as T
    }
}