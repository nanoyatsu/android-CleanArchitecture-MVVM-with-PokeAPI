package com.nanoyatsu.example.clean.poke.data.repository

import androidx.paging.PagedList
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveNetworkState
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveRefreshingState
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.data.database.dao.PokeDao
import com.nanoyatsu.example.clean.poke.data.database.entity.PokeIndexCache
import com.nanoyatsu.example.clean.poke.data.resource.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList

class PokeIndexBoundaryCallback(
    private val dao: PokeDao,
    private val networkResource: PokeNetworkResource,
    val networkState: LiveNetworkState,
    val isRefreshing: LiveRefreshingState
) : PagedList.BoundaryCallback<PokeNameImage>() {
    companion object {
        const val PAGE_SIZE = 30
    }

    private var retry: (() -> Unit)? = null

    private fun tryLoad(
        getter: () -> NamedApiResourceList,
        retry: () -> Unit
    ) {
        try {
            val response = getter()
            val dbModel = response.results.map { PokeIndexCache.from(it) }
            dao.insertAllPokeIndex(dbModel)
        } catch (e: Exception) {
            this.retry = retry
            networkState.postValue(NetworkState.Failed(e.message ?: "unknown error"))
        }
    }

    override fun onZeroItemsLoaded() {
        isRefreshing.postValue(true)
        val getter = { networkResource.list(0, PAGE_SIZE) }
        tryLoad(getter) { onZeroItemsLoaded() }
        isRefreshing.postValue(false)
    }

    override fun onItemAtEndLoaded(itemAtEnd: PokeNameImage) {
        val getter = { networkResource.list(itemAtEnd.number, PAGE_SIZE) }
        tryLoad(getter) { onItemAtEndLoaded(itemAtEnd) }
    }

    override fun onItemAtFrontLoaded(itemAtFront: PokeNameImage) {
        // nothing to do
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }
}