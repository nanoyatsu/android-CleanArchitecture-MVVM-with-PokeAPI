package com.nanoyatsu.example.clean.poke.presentation.index

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nanoyatsu.example.clean.poke.databinding.ItemIndexBinding
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.PokeNameImage

class IndexItemViewHolder(
    private val binding: ItemIndexBinding,
    private val navigation: Navigation
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup, navigation: Navigation): IndexItemViewHolder {
            val bind = ItemIndexBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return IndexItemViewHolder(bind, navigation)
        }
    }

    fun bind(data: PokeNameImage) {
        binding.data = data
        binding.nav = navigation
    }

    interface Navigation {
        fun transDetail(number: Int)
    }
}