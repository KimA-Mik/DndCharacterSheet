package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kima.dndcharactersheet.databinding.ListItemCharacteristicBinding

class CharacteristicsAdapter :
    RecyclerView.Adapter<CharacteristicViewHolder>() {
    var characteristics = emptyList<Characteristic>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacteristicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCharacteristicBinding.inflate(inflater, parent, false)
        return CharacteristicViewHolder(binding)
    }

    override fun getItemCount() = characteristics.size

    override fun onBindViewHolder(holder: CharacteristicViewHolder, position: Int) {
        val characteristic = characteristics[position]
        holder.binding.apply {
            characteristicNameTextView.setText(characteristic.title)
            characteristicValueTextView.text = characteristic.value.toString()
            skillsContainer.removeAllViewsInLayout()
        }
    }
}