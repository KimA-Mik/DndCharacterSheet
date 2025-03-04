package ru.kima.dndcharactersheet.ui.editCharacter.info

import android.text.Editable
import android.text.TextWatcher

class InfoTextWatcher(private val type: InfoType, private val listener: InfoUpdateListener) :
    TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        listener.onInfoUpdated(type, s.toString())
    }
}