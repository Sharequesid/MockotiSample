package com.example.mockotisample.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mockotisample.R
import com.example.mockotisample.model.Pokemon
import com.example.mockotisample.utils.toEntryNumber
import com.example.mockotisample.utils.toImageUrlById

class PokedexAdapter(
    private val listener: OnClickListener
) : ListAdapter<Pokemon, PokemonViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    private companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    interface OnClickListener {
        fun onPokemonClicked(pokemon: Pokemon)
    }
}

class PokemonViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

    var name: TextView
    var entryNumber: TextView
    var image: ImageView

    init {
        name = item.findViewById<View>(R.id.name) as TextView
        entryNumber = item.findViewById<View>(R.id.entry_number) as TextView

        image = item.findViewById(R.id.image) as ImageView

    }

    fun bind(pokemon: Pokemon, listener: PokedexAdapter.OnClickListener) {
        name.text = pokemon.name
        entryNumber.text = pokemon.entryNumber.toEntryNumber()
        //binding.favorite.visibility = if (pokemon.isFavorite) View.VISIBLE else View.GONE
        Log.d("PokedexAdapter","Success: ${pokemon.isFavorite}")
        image.load(pokemon.entryNumber.toImageUrlById()) {
            crossfade(true)
            transformations(CircleCropTransformation())
            decoderFactory(SvgDecoder.Factory())
        }
        item.setOnClickListener {
            listener.onPokemonClicked(pokemon)
        }
    }

    companion object {
        fun from(parent: ViewGroup): PokemonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_pokemon, parent, false)
            return PokemonViewHolder(view)
        }
    }
}
