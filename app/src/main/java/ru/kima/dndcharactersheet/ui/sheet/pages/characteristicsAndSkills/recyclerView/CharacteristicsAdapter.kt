package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.kima.dndcharactersheet.databinding.ListItemCharacteristicBinding
import ru.kima.dndcharactersheet.dnd.DndUtilities
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillView
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillViewListener
import ru.kima.dndcharactersheet.util.Math.toSignedString

class CharacteristicsAdapter(
    private val parentContext: Context,
    private val skillListener: SkillViewListener,
    private val characteristicListener: CharacteristicListener
) :
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
            characteristicNameTextView.setText(characteristic.type.titleId)
            characteristicValueTextView.text = characteristic.value.toString()

            val modifier = DndUtilities.getCharacteristicsModifier(characteristic.value)
            characteristicCheckModifierTextView.text =
                toSignedString(modifier)

            saveThrowCheckBoxDot.isVisible = characteristic.isSaveThrowChecked
            val saveThrowModifier = if (characteristic.isSaveThrowChecked) {
                modifier + 3
            } else {
                modifier
            }
            saveThrowCheckModifierTextView.text = toSignedString(saveThrowModifier)

            characteristicCheckButton.setOnClickListener {
                characteristicListener.onCharacteristicRoll(characteristic.type)
            }

            saveThrowCheckButton.setOnClickListener {
                characteristicListener.onCharacteristicSaveThrow(characteristic.type)
            }

            saveThrowCheckBox.setOnClickListener {
                characteristicListener.onSaveThrowCheckChanged(characteristic.type)
            }

            characteristicValueTextView.setOnClickListener {
                characteristicListener.onEditCharacteristicValue(characteristic.type)
            }

            skillsContainer.removeAllViewsInLayout()
            for (skill in characteristic.skills) {
                val view = SkillView(skill, skillListener, parentContext)
                skillsContainer.addView(view)
            }
        }
    }
}