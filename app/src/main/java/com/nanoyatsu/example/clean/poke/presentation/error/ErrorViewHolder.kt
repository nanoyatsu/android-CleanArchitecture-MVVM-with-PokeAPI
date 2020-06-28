package com.nanoyatsu.example.clean.poke.presentation.error

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nanoyatsu.example.clean.poke.databinding.ItemErrorBinding

class ErrorViewHolder(
    val binding: ItemErrorBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ErrorViewHolder {
            val bind = ItemErrorBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ErrorViewHolder(bind)
        }
    }

    fun bind(message: String, retry: () -> Unit) {
        binding.message.text = message
        binding.buttonRetry.setOnClickListener { retry() }
    }
}