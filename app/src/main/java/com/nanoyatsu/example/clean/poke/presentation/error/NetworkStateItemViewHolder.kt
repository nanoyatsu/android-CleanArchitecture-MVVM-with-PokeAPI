package com.nanoyatsu.example.clean.poke.presentation.error

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nanoyatsu.example.clean.poke.databinding.ItemNetworkStateBinding

class NetworkStateItemViewHolder(
    val binding: ItemNetworkStateBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): NetworkStateItemViewHolder {
            val bind = ItemNetworkStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return NetworkStateItemViewHolder(bind)
        }
    }

    fun bind(message: String?, retry: () -> Unit) {
        binding.message.text = message
        binding.buttonRetry.setOnClickListener { retry() }
    }
}