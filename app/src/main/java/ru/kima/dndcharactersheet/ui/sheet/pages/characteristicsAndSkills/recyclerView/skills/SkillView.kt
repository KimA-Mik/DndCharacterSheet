package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ru.kima.dndcharactersheet.databinding.ListItemSkillBinding
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill

class SkillView @JvmOverloads constructor(
    skill: Skill,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {
    init {
        val inflater = LayoutInflater.from(context)
        val binding = ListItemSkillBinding.inflate(inflater, this, true)
        binding.apply {
            skillCheckModifierTextView.text = skill.level.toString()
            skillTitleTextView.setText(skill.type.titleId)
        }
    }
}