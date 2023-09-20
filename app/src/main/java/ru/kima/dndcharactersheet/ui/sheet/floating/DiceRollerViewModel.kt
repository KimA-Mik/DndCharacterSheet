package ru.kima.dndcharactersheet.ui.sheet.floating

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DiceRollerViewModel : ViewModel() {
    enum class FloatingMenuState {
        Closed,
        Opened,
        Ready
    }

    private val _floatingMenuState = MutableStateFlow(FloatingMenuState.Closed)
    val floatingMenuState = _floatingMenuState.asStateFlow()

    private val dices = mutableMapOf<Int, Int>()

    fun onDiceButtonClicked() {
        when (_floatingMenuState.value) {
            FloatingMenuState.Closed -> {
                dices.clear()
                _floatingMenuState.value = FloatingMenuState.Opened
            }

            FloatingMenuState.Opened -> {
                _floatingMenuState.value = FloatingMenuState.Closed
            }

            FloatingMenuState.Ready -> {

            }
        }
    }

    fun addDice(sides: Int) {
        if (dices.containsKey(sides)) {
            dices[sides] = dices[sides]!! + 1
        } else {
            dices[sides] = 1
        }
    }

    private fun removeDice(sides: Int) {
        if (dices.containsKey(sides) &&
            dices[sides]!! > 0
        ) {
            dices[sides] = dices[sides]!! - 1
        }
    }

    fun onRemoveDice(sides: Int): Boolean {
        removeDice(sides)
        return true
    }
}