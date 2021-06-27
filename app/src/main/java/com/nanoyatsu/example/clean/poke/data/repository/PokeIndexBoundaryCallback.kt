package com.nanoyatsu.example.clean.poke.data.repository

import androidx.paging.PagedList
import com.apollographql.apollo.api.Response
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveNetworkState
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveRefreshingState
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.data.resource.database.dao.PokeIndexDao
import com.nanoyatsu.example.clean.poke.data.resource.database.entity.PokeIndexCache
import com.nanoyatsu.example.clean.poke.data.resource.network.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.data.resource.network.graphql.pokemon.query.FetchPokemonsQuery
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.PokeNameImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Paging Libraryを使いたい関係で、UseCase部の裏口を通っている感が強い（再考の余地がありそう）
 */
class PokeIndexBoundaryCallback(
    private val dao: PokeIndexDao,
    private val networkResource: PokeNetworkResource,
    val networkState: LiveNetworkState,
    val isRefreshing: LiveRefreshingState
) : PagedList.BoundaryCallback<PokeNameImage>() {
    companion object {
        const val PAGE_SIZE = 30
    }

    private var retry: (() -> Unit)? = null

    private suspend fun tryLoad(
        getter: suspend () -> Response<FetchPokemonsQuery.Data>,
        retry: () -> Unit
    ) {
        try {
            val response = getter()
            val data = response.data
            if (data == null) {
                val errors = requireNotNull(response.errors) { "errors are null." }
                val message = if (errors.isNotEmpty()) errors.first().message
                else "error is empty"
                throw Exception(message)
            }
            requireNotNull(data.pokemons) { "response data is null." }
            val dbModel = data.pokemons.map { PokeIndexCache.from(requireNotNull(it)) }
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
            val getter = suspend { networkResource.list(PokeNetworkResource.FETCH_POKEMONS_COUNT) }
            tryLoad(getter) { onZeroItemsLoaded() }
            isRefreshing.postValue(false)

        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: PokeNameImage) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val getter = suspend { networkResource.list(itemAtEnd.number, PAGE_SIZE) }
//            tryLoad(getter) { onItemAtEndLoaded(itemAtEnd) }
//        }
        // 151件取得固定なのでいったん何もしない
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