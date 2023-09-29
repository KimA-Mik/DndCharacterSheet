package ru.kima.dndcharactersheet.ui.sheet.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kima.dndcharactersheet.ui.sheet.pages.attacksAndSkills.AttacksAndSkillsFragment
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndAbilities.CharacteristicsAndAbilitiesFragment

class SheetPagerAdapter(
    fragment: Fragment,
    private val characterId: Int
) :
    FragmentStateAdapter(fragment) {
    private val pages = Page.entries
    override fun getItemCount() = pages.size

    override fun createFragment(position: Int): Fragment {
        val args = Bundle()
        args.putInt(CHARACTER_ID, characterId)
        return when (pages[position]) {
            Page.CHARACTERISTICS_AND_ABILITIES -> {
                val fragment = CharacteristicsAndAbilitiesFragment()
                fragment.arguments = args
                fragment
            }

            Page.ATTACKS_AND_SKILLS -> {
                val fragment = AttacksAndSkillsFragment()
                fragment.arguments = args
                fragment
            }
        }
    }

}