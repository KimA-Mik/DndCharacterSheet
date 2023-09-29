package ru.kima.dndcharactersheet.ui.sheet.pages.attacksAndSkills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kima.dndcharactersheet.databinding.PageAttacksAndSkillsBinding

class AttacksAndSkillsFragment : Fragment() {
    private var _binding: PageAttacksAndSkillsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageAttacksAndSkillsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}