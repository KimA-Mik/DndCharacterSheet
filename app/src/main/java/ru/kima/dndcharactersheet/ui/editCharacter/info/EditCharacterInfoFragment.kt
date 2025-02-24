package ru.kima.dndcharactersheet.ui.editCharacter.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.databinding.FragmentEditCharacterInfoBinding

class EditCharacterInfoFragment : Fragment() {
    private var _binding: FragmentEditCharacterInfoBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Cannot access binding because it is null. Is the view visible?" }

    private val args: EditCharacterInfoFragmentArgs by navArgs()
    private val viewModel: EditCharacterInfoViewModel by viewModels {
        EditCharacterInfoViewModelFactory(args.characterId)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCharacterInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.character.collect {
                        collectCharacter(it)
                    }
                }

                launch {
                    viewModel.needSave.collect { visible ->
                        if (visible) {
                            binding.saveInfoFab.show()
                        } else {
                            binding.saveInfoFab.hide()
                        }
                    }
                }
            }
        }

        binding.apply {
            characterNameTextField.editText?.addTextChangedListener(
                InfoTextWatcher(
                    InfoType.NAME,
                    viewModel
                )
            )
            characterRaceTextField.editText?.addTextChangedListener(
                InfoTextWatcher(
                    InfoType.RACE,
                    viewModel
                )
            )
            characterClassTextField.editText?.addTextChangedListener(
                InfoTextWatcher(
                    InfoType.CLASS,
                    viewModel
                )
            )
            characterArmorClassTextField.editText?.addTextChangedListener(
                InfoTextWatcher(
                    InfoType.AC,
                    viewModel
                )
            )
            characterSpeedTextField.editText?.addTextChangedListener(
                InfoTextWatcher(
                    InfoType.SPEED,
                    viewModel
                )
            )
            characterInitiativeTextField.editText?.addTextChangedListener(
                InfoTextWatcher(
                    InfoType.INITIATIVE,
                    viewModel
                )
            )

            saveInfoFab.setOnClickListener {
                viewModel.onSave(
                    characterNameTextField.editText!!.text.toString(),
                    characterRaceTextField.editText!!.text.toString(),
                    characterClassTextField.editText!!.text.toString(),
                    characterArmorClassTextField.editText!!.text.toString(),
                    characterSpeedTextField.editText!!.text.toString(),
                    characterInitiativeTextField.editText!!.text.toString(),
                )
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    INFO_EDITED_KEY,
                    true
                )
            }
        }

    }

    private fun collectCharacter(character: CharacterEntity) {
        binding.apply {
            characterNameTextField.editText?.setText(character.name)
            characterRaceTextField.editText?.setText(character.race)
            characterClassTextField.editText?.setText(character.charClass)
            characterArmorClassTextField.editText?.setText(character.armorClass)
            characterSpeedTextField.editText?.setText(character.speed)
            characterInitiativeTextField.editText?.setText(character.initiative)
        }
    }
}