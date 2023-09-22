package ru.kima.dndcharactersheet.ui.characterslist.recyclerview

import androidx.recyclerview.widget.DiffUtil
import ru.kima.dndcharactersheet.data.entities.CharacterEntity

class CharactersListDiffCallback(
    private val oldList: List<CharacterEntity>,
    private val newList: List<CharacterEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
