package ru.kima.dndcharactersheet.ui.characterslist.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import ru.kima.dndcharactersheet.R

class CharacterListMenuProvider(
    private val listener: CharacterListMenuListener
) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_item_settings -> {
                listener.onOpenSetting()
                true
            }

            else -> false
        }
    }
}