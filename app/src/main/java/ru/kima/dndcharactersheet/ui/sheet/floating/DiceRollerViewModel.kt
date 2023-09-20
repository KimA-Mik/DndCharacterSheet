package ru.kima.dndcharactersheet.ui.sheet.floating

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DiceRollerViewModel: ViewModel() {
    enum class FloatingMenuState {
        Closed,
        Opened,
        Ready
    }
    private val _floatingMenuState = MutableStateFlow(FloatingMenuState.Opened)
    val floatingMenuState = _floatingMenuState.asStateFlow()

    private val dices = mutableMapOf<Int, Int>()

    fun onDiceButtonClicked() {
        if (_floatingMenuState.value == FloatingMenuState.Closed) {
            _floatingMenuState.value = FloatingMenuState.Opened
        } else {
            _floatingMenuState.value = FloatingMenuState.Closed
        }
    }
}