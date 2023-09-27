package ru.kima.dndcharactersheet.ui.sheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import ru.kima.dndcharactersheet.R
import ru.kima.dndcharactersheet.databinding.FragmentCharacterSheetBinding
import ru.kima.dndcharactersheet.ui.factory
import kotlin.math.max

class CharacterSheetFragment : Fragment() {
    private var _binding: FragmentCharacterSheetBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: CharacterSheetViewModel by viewModels() { factory() }
    private val args: CharacterSheetFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadCharacter(args.characterId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //(character.level + 1).toString() causes this warning
    //Although it sums to ints and converts result to sting
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateXpBar(59)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.character.collect { character ->
                        binding.characterNameTextView.text = character.name
                        binding.raceAndClassTextView.text = getString(
                            R.string.race_and_class,
                            character.race,
                            character.charClass
                        )
                        binding.nextLevelTextView.text = (character.level + 1).toString()
                        binding.currentLevelTextView.text =
                            getString(R.string.level, character.level)

                        val xpToCurrentLevel = viewModel.dndUtilities.getXpToLvlUp(character.level)
                        val xpToNextLevel = viewModel.dndUtilities.getXpToLvlUp(character.level + 1)
                        val xpToLvlUp = xpToNextLevel - xpToCurrentLevel
                        val currentXp = max(character.experiencePoints - xpToCurrentLevel, 0)
                        binding.xpTextView.text =
                            getString(R.string.hp, character.experiencePoints, xpToNextLevel)
                        val xpBarPercent = currentXp * 100 / xpToLvlUp
                        //Thanks to https://stackoverflow.com/a/64025316
                        //Request xp bar resize as it container is laid out correctly
                        binding.xpProgressBarContainer.doOnLayout {
                            updateXpBar(xpBarPercent)
                        }
                    }
                }
            }
        }
//                        val parent = binding.xpProgressBar.parent as FrameLayout
        binding.debugSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                updateXpBar(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        }
        )
    }

    private fun updateXpBar(percent: Int) {
        val newVal = if (percent > 100) 100
        else if (percent < 0) 0
        else percent
        val width = binding.xpProgressBarContainer.width * newVal / 100
        val params = ViewGroup.LayoutParams(width, MATCH_PARENT)
        binding.xpProgressBar.layoutParams = params
    }
}