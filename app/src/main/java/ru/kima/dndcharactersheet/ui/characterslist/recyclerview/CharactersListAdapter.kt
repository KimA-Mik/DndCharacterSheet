package ru.kima.dndcharactersheet.ui.characterslist.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kima.dndcharactersheet.R
import ru.kima.dndcharactersheet.data.entities.CharacterEntity
import ru.kima.dndcharactersheet.databinding.ListItemCharacterBinding
import ru.kima.dndcharactersheet.ui.characterslist.CharacterListListener

class CharactersListAdapter(val context: Context, private val listener: CharacterListListener) :
    RecyclerView.Adapter<CharacterViewHolder>() {
    var characters: List<CharacterEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.binding.apply {
            nameTextView.text = character.name
            classTextView.text = character.charClass
            raceTextView.text = character.race
            hpTextView.text = context.getString(R.string.hp, character.currentHp, character.maxHp)

            root.setOnClickListener { listener.onListItemClicked(character.id) }
        }
    }
}