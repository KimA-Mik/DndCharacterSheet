package ru.kima.dndcharactersheet.ui.sheet.floating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.kima.dndcharactersheet.R
import ru.kima.dndcharactersheet.databinding.FragmentDiceRollerBinding

class DiceRollerFragment : Fragment() {
    private var _binding: FragmentDiceRollerBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: DiceRollerViewModel by viewModels()

    private val diceTextViews = mutableMapOf<Int, TextView>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiceRollerBinding.inflate(layoutInflater, container, false)
        diceTextViews[2] = binding.d2TextView
        diceTextViews[4] = binding.d4TextView
        diceTextViews[6] = binding.d6TextView
        diceTextViews[8] = binding.d8TextView
        diceTextViews[10] = binding.d10TextView
        diceTextViews[12] = binding.d12TextView
        diceTextViews[20] = binding.d20TextView
        diceTextViews[100] = binding.d100TextView
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        diceTextViews.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.floatingMenuState.collect { state ->
                        when (state) {
                            DiceRollerViewModel.FloatingMenuState.Closed -> {
                                binding.buttonsGroup.isVisible = false
                                binding.buttonsGroup.isClickable = false
                                binding.diceButton.setText(R.string.dice_button_closed_text)
                                binding.diceButton.setIconResource(ID_NULL)
                                diceTextViews.forEach { it.value.isVisible = false }
                            }

                            DiceRollerViewModel.FloatingMenuState.Opened -> {
                                binding.buttonsGroup.isVisible = true
                                binding.buttonsGroup.isClickable = true
                                binding.diceButton.text = ""
                                binding.diceButton.setIconResource(R.drawable.ic_close)
                            }

                            DiceRollerViewModel.FloatingMenuState.Ready -> {
                                binding.diceButton.setText(R.string.dice_button_ready_text)
                                binding.diceButton.setIconResource(ID_NULL)
                            }
                        }
                    }
                }

                launch {
                    viewModel.dice.collect { dice ->
                        dice.forEach { (sides, count) ->
                            diceTextViews[sides]?.let { textView ->
                                if (count > 0) {
                                    textView.text = count.toString()
                                    textView.isVisible = true
                                } else {
                                    textView.isVisible = false
                                }
                            }
                        }
                    }
                }
            }
        }
        binding.diceButton.setOnClickListener { viewModel.onDiceButtonClicked() }
        binding.diceD2Button.setOnClickListener { viewModel.addDice(2) }
        binding.diceD2Button.setOnLongClickListener { viewModel.onRemoveDice(2) }
        binding.diceD4Button.setOnClickListener { viewModel.addDice(4) }
        binding.diceD4Button.setOnLongClickListener { viewModel.onRemoveDice(4) }
        binding.diceD6Button.setOnClickListener { viewModel.addDice(6) }
        binding.diceD6Button.setOnLongClickListener { viewModel.onRemoveDice(6) }
        binding.diceD8Button.setOnClickListener { viewModel.addDice(8) }
        binding.diceD8Button.setOnLongClickListener { viewModel.onRemoveDice(8) }
        binding.diceD10Button.setOnClickListener { viewModel.addDice(10) }
        binding.diceD10Button.setOnLongClickListener { viewModel.onRemoveDice(10) }
        binding.diceD12Button.setOnClickListener { viewModel.addDice(12) }
        binding.diceD12Button.setOnLongClickListener { viewModel.onRemoveDice(12) }
        binding.diceD20Button.setOnClickListener { viewModel.addDice(20) }
        binding.diceD20Button.setOnLongClickListener { viewModel.onRemoveDice(20) }
        binding.diceD100Button.setOnClickListener { viewModel.addDice(100) }
        binding.diceD100Button.setOnLongClickListener { viewModel.onRemoveDice(100) }
    }
}