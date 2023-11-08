package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ru.kima.dndcharactersheet.databinding.ListItemSkillBinding
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill
import ru.kima.dndcharactersheet.util.Math.toSignedString

class SkillView @JvmOverloads constructor(
    skill: Skill,
    listener: SkillViewListener,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: ListItemSkillBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ListItemSkillBinding.inflate(inflater, this, true)
        binding.apply {
            skillCheckButton.setOnClickListener { listener.onRoll(skill.type) }
            skillCheckbox.setOnClickListener { listener.onSelect(skill.type) }
            skillCheckModifierTextView.text = toSignedString(skill.modifier + skill.level * 3)
            skillTitleTextView.setText(skill.type.titleId)
            setLevel(skill.level)
        }
    }

    private fun setLevel(level: Int) {
        when (level) {
            0 -> {
                binding.skillCheckNoneImageView.isVisible = true
                binding.skillCheckSingleImageView.isVisible = false
                binding.skillCheckDoubleImageView.isVisible = false
            }

            1 -> {
                binding.skillCheckNoneImageView.isVisible = true
                binding.skillCheckSingleImageView.isVisible = true
                binding.skillCheckDoubleImageView.isVisible = false
            }

            2 -> {
                binding.skillCheckNoneImageView.isVisible = true
                binding.skillCheckSingleImageView.isVisible = true
                binding.skillCheckDoubleImageView.isVisible = true
            }

            else -> {
                binding.skillCheckNoneImageView.isVisible = false
                binding.skillCheckSingleImageView.isVisible = false
                binding.skillCheckDoubleImageView.isVisible = false
            }
        }
    }
}