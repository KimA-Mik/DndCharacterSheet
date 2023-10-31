package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

import androidx.recyclerview.widget.DiffUtil

class CharacteristicsDiffCallback(
    private val old: List<Characteristic>,
    private val new: List<Characteristic>
) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = old[oldItemPosition]
        val newItem = new[newItemPosition]
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = old[oldItemPosition]
        val newItem = new[newItemPosition]
        if (oldItem.skills.size != newItem.skills.size) {
            return false
        }

        if (oldItem.isSaveThrowChecked != newItem.isSaveThrowChecked) {
            return false
        }

        for (i in newItem.skills.indices) {
            val oldSkill = oldItem.skills[i]
            val newSkill = newItem.skills[i]

            if (oldSkill != newSkill) {
                return false
            }
        }
        return true
    }
}