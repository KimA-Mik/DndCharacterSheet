package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kima.dndcharactersheet.databinding.ListItemCharacteristicBinding
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillView

class CharacteristicsAdapter(private val parentContext: Context) :
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

            for (skill in characteristic.skills) {
                val view = SkillView(skill, parentContext)
                skillsContainer.addView(view)
            }
        }
    }
}