package ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.kima.dndcharactersheet.databinding.PageCharacteristicsAndSkillsBinding
import ru.kima.dndcharactersheet.ui.dialogs.DIALOG_SUCCESS_FLAG
import ru.kima.dndcharactersheet.ui.dialogs.EDIT_NUMBER_DIALOG_REQUEST
import ru.kima.dndcharactersheet.ui.dialogs.EDIT_NUMBER_DIALOG_REQUEST_KEY
import ru.kima.dndcharactersheet.ui.dialogs.EditNumberDialog
import ru.kima.dndcharactersheet.ui.factory
import ru.kima.dndcharactersheet.ui.sheet.pager.CHARACTER_ID
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.CharacteristicsAdapter
import ru.kima.dndcharactersheet.ui.sheet.pages.characteristicsAndSkills.recyclerView.CharacteristicsDiffCallback

class CharacteristicsAndSkillsFragment : Fragment() {
    private var _binding: PageCharacteristicsAndSkillsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: CharacteristicsAndSkillsViewModel by viewModels { factory() }
    private val adapter: CharacteristicsAdapter by lazy {
        CharacteristicsAdapter(
            requireContext(),
            viewModel,
            viewModel
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val charId = requireArguments().getInt(CHARACTER_ID)
        viewModel.load(charId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageCharacteristicsAndSkillsBinding.inflate(layoutInflater, container, false)
        binding.apply {
            characteristicsRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())
            characteristicsRecyclerView.adapter = adapter
            characteristicsRecyclerView.itemAnimator = null
        }

        parentFragmentManager.setFragmentResultListener(
            EDIT_NUMBER_DIALOG_REQUEST_KEY,
            viewLifecycleOwner
        ) { requestKey, bundle ->
            when (requestKey) {
                EDIT_NUMBER_DIALOG_REQUEST_KEY -> {
                    if (!bundle.containsKey(DIALOG_SUCCESS_FLAG)) {
                        return@setFragmentResultListener
                    }

                    val success = bundle.getBoolean(DIALOG_SUCCESS_FLAG)
                    if (!success) {
                        return@setFragmentResultListener
                    }

                    val newValue = bundle.getInt(EDIT_NUMBER_DIALOG_REQUEST)
                    viewModel.onUpdateCharacteristicValue(newValue)
                }
            }
        }

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
                    viewModel.characteristic.collect { characteristics ->
                        val diffCallback =
                            CharacteristicsDiffCallback(adapter.characteristics, characteristics)
                        val diff = DiffUtil.calculateDiff(diffCallback)
                        adapter.characteristics = characteristics
                        diff.dispatchUpdatesTo(adapter)
                    }
                }

                launch {
                    viewModel.characteristicEditEvent.collect { characteristic ->
                        val editFragment = EditNumberDialog()

                        editFragment.config(
                            characteristic.type.titleId,
                            characteristic.value
                        )

                        editFragment.show(parentFragmentManager, "edit_characteristic")
                    }
                }
            }
        }
//        binding.button.setOnClickListener { viewModel.roll() }
    }
}