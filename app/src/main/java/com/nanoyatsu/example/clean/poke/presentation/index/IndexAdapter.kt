package com.nanoyatsu.example.clean.poke.presentation.index

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nanoyatsu.example.clean.poke.R
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.PokeNameImage
import com.nanoyatsu.example.clean.poke.presentation.error.NetworkStateItemViewHolder
import kotlin.reflect.KFunction0

// memo : エラー表示で拡張するのでRecyclerView.ViewHolderのまま
class IndexAdapter(
    private val navigation: IndexItemViewHolder.Navigation,
    private val doRetry: KFunction0<Unit?>
) : PagedListAdapter<PokeNameImage, RecyclerView.ViewHolder>(DiffCallback()) {
    private var networkState: NetworkState? = NetworkState.Success

    // 使用するViewHolderを判定する（＝使用するレイアウト一覧になる 今回はエラー表示だけ対応
    override fun getItemViewType(position: Int): Int {
        return if (!hasExtraRow(networkState) || position < super.getItemCount())
            R.layout.item_index
        else
            R.layout.item_network_state
    }

    override fun getItemCount(): Int =
        super.getItemCount() + if (hasExtraRow(networkState)) 1 else 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_index -> IndexItemViewHolder.from(parent, navigation)
            R.layout.item_network_state -> NetworkStateItemViewHolder.from(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IndexItemViewHolder) {
            val data = getItem(position)
            holder.bind(data!!)
        }
        if (holder is NetworkStateItemViewHolder && networkState is NetworkState.Failed) {
            holder.bind((networkState as NetworkState.Failed).msg, doRetry)
        }
    }

    // github android/architecture-components-samples/PagingWithNetworkSample より
    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        this.networkState = newNetworkState
        val hadExtraRow = hasExtraRow(previousState)
        val hasExtraRow = hasExtraRow(newNetworkState)
        if (hadExtraRow != hasExtraRow) { // 前回と今回が違う
            if (hadExtraRow) notifyItemRemoved(super.getItemCount()) // 前回ExtraRowがある -> 削除
            else notifyItemInserted(super.getItemCount()) // 前回ExtraRowがない -> 追加
        } else if (hasExtraRow && previousState != newNetworkState) { // 今回ExtraRowがあって、前回と異なる
            notifyItemChanged(super.getItemCount())
        }
    }

    private fun hasExtraRow(state: NetworkState?) =
        state != null && (state is NetworkState.Failed)

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<PokeNameImage>() {
            override fun areItemsTheSame(old: PokeNameImage, new: PokeNameImage): Boolean =
                old.name == new.name

            override fun areContentsTheSame(old: PokeNameImage, new: PokeNameImage): Boolean =
                old == new
        }
    }
}