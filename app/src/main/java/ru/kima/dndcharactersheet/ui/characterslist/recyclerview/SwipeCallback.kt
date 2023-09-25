package ru.kima.dndcharactersheet.ui.characterslist.recyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeCallback(private val listener: SwipeListener) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.RIGHT or
            ItemTouchHelper.LEFT
) {
    //Not Used
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemDismiss(viewHolder.absoluteAdapterPosition)
    }
}