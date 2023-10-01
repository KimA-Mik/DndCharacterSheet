package ru.kima.dndcharactersheet.ui.characterslist

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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.kima.dndcharactersheet.R
import ru.kima.dndcharactersheet.databinding.FragmentCharactersListBinding
import ru.kima.dndcharactersheet.ui.characterslist.recyclerview.CharactersListAdapter
import ru.kima.dndcharactersheet.ui.characterslist.recyclerview.CharactersListDiffCallback
import ru.kima.dndcharactersheet.ui.characterslist.recyclerview.SwipeCallback
import ru.kima.dndcharactersheet.ui.factory

class CharactersListFragment : Fragment() {
    private var _binding: FragmentCharactersListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: CharacterListViewModel by viewModels() { factory() }
    private val adapter: CharactersListAdapter by lazy {
        CharactersListAdapter(
            requireContext(),
            viewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersListBinding.inflate(layoutInflater, container, false)

        //Config recyclerView
        binding.charactersRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.charactersRecyclerView.adapter = adapter
        val touchHelper = ItemTouchHelper(SwipeCallback(viewModel))
        touchHelper.attachToRecyclerView(binding.charactersRecyclerView)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createCharacterFab.setOnClickListener { viewModel.createCharacter() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.characters.collect { characters ->
                        val diffCallback =
                            CharactersListDiffCallback(adapter.characters, characters)
                        val diffRolls = DiffUtil.calculateDiff(diffCallback)
                        adapter.characters = characters
                        diffRolls.dispatchUpdatesTo(adapter)
                    }
                }
                launch {
                    viewModel.showSheet.collect { navEvent ->
                        navEvent.getValue()?.let { charId ->
                            findNavController().navigate(
                                CharactersListFragmentDirections.showCharacterSheet(charId)
                            )
                        }
                    }
                }
            }
        }

        binding.toolbar.inflateMenu(R.menu.menu_cheracters_list)
    }
}