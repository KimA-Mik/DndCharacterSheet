package ru.kima.dndcharactersheet.ui.sheet.floating.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.kima.dndcharactersheet.R
import ru.kima.dndcharactersheet.databinding.ListItemLastRollBinding
import ru.kima.dndcharactersheet.databinding.ListItemPrevRollBinding
import ru.kima.dndcharactersheet.ui.sheet.event.EventRoll

open class DiceRollHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
class LastRollHolder(binding: ListItemLastRollBinding) : DiceRollHolder(binding)
class PrevRollHolder(binding: ListItemPrevRollBinding) : DiceRollHolder(binding)


class DiceRollAdapter(private val listListener: RollListListener) :
    RecyclerView.Adapter<DiceRollHolder>() {
    var rolls: List<DiceRoll> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceRollHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.list_item_last_roll -> {
                val binding = ListItemLastRollBinding.inflate(inflater, parent, false)
                LastRollHolder(binding)
            }

            R.layout.list_item_prev_roll -> {
                val binding = ListItemPrevRollBinding.inflate(inflater, parent, false)
                PrevRollHolder(binding)
            }

            else -> {
                throw IllegalArgumentException("Unsupported layout")
            }
        }
    }

    override fun getItemCount(): Int = rolls.size

    override fun onBindViewHolder(holder: DiceRollHolder, position: Int) {
        val roll = rolls[position]

        when (holder) {
            is LastRollHolder -> {
                val binding = holder.binding as ListItemLastRollBinding
                binding.apply {
                    totalResultTextView.text = roll.sum.toString()

                    lastRollValueTextView.isVisible = roll.value != EventRoll.Value.NONE
                    lastRollValueTextView.setText(roll.value.strId)
                    lastRollTypeTextView.setText(roll.type.strId)
                    resultsTextView.text = roll.results
                    diceTypeTextView.text = roll.dice

                    clearListFab.setOnClickListener { listListener.onClearListPressed() }
                }
            }

            is PrevRollHolder -> {
                val binding = holder.binding as ListItemPrevRollBinding
                binding.apply {
                    totalResultTextView.text = roll.sum.toString()
                    lastRollValueTextView.setText(roll.value.strId)
                    lastRollTypeTextView.setText(roll.type.strId)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == rolls.lastIndex) {
            R.layout.list_item_last_roll
        } else {
            R.layout.list_item_prev_roll
        }
    }
}