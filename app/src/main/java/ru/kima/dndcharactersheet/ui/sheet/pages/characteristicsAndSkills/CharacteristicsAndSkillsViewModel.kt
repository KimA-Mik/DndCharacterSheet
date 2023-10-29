package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.kima.dndcharactersheet.R
import ru.kima.dndcharactersheet.model.CharactersDatabaseService
import ru.kima.dndcharactersheet.ui.sheet.event.RollType
import ru.kima.dndcharactersheet.ui.sheet.event.RollValue
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Characteristic
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.Skill
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.skills.SkillType
import ru.kima.dndcharactersheet.ui.sheet.pages.listeners.CharacteristicsAndAbilitiesListener

class CharacteristicsAndSkillsViewModel(
    private val listener: CharacteristicsAndAbilitiesListener
) :
    ViewModel(),
    KoinComponent {
    private val databaseService: CharactersDatabaseService by inject()
    val characteristics = listOf(
        Characteristic(
            R.string.characteristic_strength, 19,
            listOf(
                Skill(SkillType.ATHLETICS, 1),
                Skill(SkillType.ACROBATICS, 25)
            )
        ),
        Characteristic(R.string.characteristic_constitution, 15, emptyList()),
        Characteristic(R.string.characteristic_dexterity, 20, emptyList()),
        Characteristic(R.string.characteristic_intelligence, 8, emptyList()),
        Characteristic(R.string.characteristic_wisdom, 3, emptyList()),
        Characteristic(R.string.characteristic_charisma, 0, emptyList())
    )

    fun roll() = viewModelScope.launch {
        listener.onRoll(RollType.CHECK, RollValue.STRENGTH)
    }
}