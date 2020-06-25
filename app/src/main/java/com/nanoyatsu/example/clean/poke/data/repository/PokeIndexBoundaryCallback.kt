package com.nanoyatsu.example.clean.poke.data.repository

import androidx.paging.PagedList
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveNetworkState
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveRefreshingState
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.data.database.dao.PokeDao
import com.nanoyatsu.example.clean.poke.data.database.entity.PokeIndexCache
import com.nanoyatsu.example.clean.poke.data.resource.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList

/**
 * Paging Libraryを使いたい関係で、UseCase部の裏口を通っている感が強い（再考の余地がありそう）
 */
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

    private suspend fun tryLoad(
        getter: suspend () -> NamedApiResourceList,
        retry: () -> Unit
    ) {
        try {
            val response = getter()
            val dbModel = response.results.map { PokeIndexCache.from(it) }
            dao.insertAllPokeIndex(dbModel)
        } catch (e: Exception) {
            e.printStackTrace()
            this.retry = retry
            networkState.postValue(NetworkState.Failed(e.message ?: "unknown error"))
        }
    }

    override fun onZeroItemsLoaded() {
        isRefreshing.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val getter = suspend { networkResource.list(0, PAGE_SIZE) }
            tryLoad(getter) { onZeroItemsLoaded() }
            isRefreshing.postValue(false)

        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: PokeNameImage) {
        CoroutineScope(Dispatchers.IO).launch {
            val getter = suspend { networkResource.list(itemAtEnd.number, PAGE_SIZE) }
            tryLoad(getter) { onItemAtEndLoaded(itemAtEnd) }
        }
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