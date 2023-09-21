package ru.kima.dndcharactersheet.ui.sheet.floating

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiceRollerViewModel : ViewModel() {
    enum class FloatingMenuState {
        Closed,
        Opened,
        Ready
    }

    private val _floatingMenuState = MutableStateFlow(FloatingMenuState.Closed)
    val floatingMenuState = _floatingMenuState.asStateFlow()

    //TODO: find a way no notify collector, when inner collection is changed
    //private val dice = mutableMapOf<Int, Int>()
    private val _dice = MutableStateFlow(mutableMapOf<Int, Int>())
    val dice = _dice.asStateFlow()

    fun onDiceButtonClicked() {
        when (_floatingMenuState.value) {
            FloatingMenuState.Closed -> {
                _dice.value.clear()
                _floatingMenuState.value = FloatingMenuState.Opened
            }

            FloatingMenuState.Opened -> {
                _floatingMenuState.value = FloatingMenuState.Closed
            }

            FloatingMenuState.Ready -> {
                _floatingMenuState.value = FloatingMenuState.Closed
            }
        }
    }

    fun addDice(sides: Int) {
        _dice.update {
            val dice = copyMap()
            if (dice.containsKey(sides)) {
                dice[sides] = dice[sides]!! + 1
            } else {
                dice[sides] = 1
            }
            dice
        }
        _floatingMenuState.value = FloatingMenuState.Ready
    }

    private fun removeDice(sides: Int) {
        _dice.update {
            val dice = copyMap()
            if (dice.containsKey(sides) &&
                dice[sides]!! > 0
            ) {
                dice[sides] = dice[sides]!! - 1
            }
            dice
        }

        for (i in _dice.value.keys) {
            if (_dice.value[i]!! > 0) {
                return
            }
        }
        _floatingMenuState.value = FloatingMenuState.Opened
    }

    fun onRemoveDice(sides: Int): Boolean {
        removeDice(sides)
        return true
    }

    private fun copyMap(): MutableMap<Int, Int> {
        val newMap = mutableMapOf<Int, Int>()
        dice.value.forEach { (sides, count) ->
            newMap[sides] = count
        }
        return newMap
    }
}