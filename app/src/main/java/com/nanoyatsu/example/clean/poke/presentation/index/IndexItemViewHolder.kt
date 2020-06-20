package com.nanoyatsu.example.clean.poke.presentation.index

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nanoyatsu.example.clean.poke.databinding.ItemIndexBinding
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage

class IndexItemViewHolder(private val binding: ItemIndexBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val navigation: Navigation =
        IndexFragment.Navigation(binding.root.context as Activity)

    companion object {
        fun from(parent: ViewGroup): IndexItemViewHolder {
            val bind = ItemIndexBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return IndexItemViewHolder(bind)
        }
    }

    fun bind(data: PokeNameImage) {
        binding.data = data
        binding.nav = navigation
    }

    interface Navigation {
        fun transDetail(id: Int)
    }
}