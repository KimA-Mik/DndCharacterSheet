package ru.kima.dndcharactersheet.ui.sheet.floating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.kima.dndcharactersheet.databinding.FragmentDiceRollerBinding

class DiceRollerFragment : Fragment() {
    private var _binding: FragmentDiceRollerBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: DiceRollerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiceRollerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.floatingMenuState.collect { state ->
                        when (state) {
                            DiceRollerViewModel.FloatingMenuState.Closed -> {
                                binding.buttonsGroup.visibility = View.GONE
                                binding.buttonsGroup.isClickable = false
                            }

                            DiceRollerViewModel.FloatingMenuState.Opened -> {
                                binding.buttonsGroup.visibility = View.VISIBLE
                                binding.buttonsGroup.isClickable = true
                            }

                            else -> {}
                        }

                    }
                }

            }
        }
        binding.diceButton.setOnClickListener { viewModel.onDiceButtonClicked() }
    }
}