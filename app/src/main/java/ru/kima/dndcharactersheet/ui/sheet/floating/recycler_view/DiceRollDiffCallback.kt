package ru.kima.dndcharactersheet.ui.sheet.floating.recycler_view

import androidx.recyclerview.widget.DiffUtil

class DiceRollDiffCallback(
    private val oldList: List<DiceRoll>,
    private val newList: List<DiceRoll>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].sum == newList[newItemPosition].sum
    }
}