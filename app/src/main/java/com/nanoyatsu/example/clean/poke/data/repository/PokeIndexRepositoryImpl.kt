package com.nanoyatsu.example.clean.poke.data.repository

import androidx.paging.LivePagedListBuilder
import com.nanoyatsu.example.clean.poke.core.dataclass.Listing
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveNetworkState
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveRefreshingState
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.data.resource.database.dao.PokeIndexDao
import com.nanoyatsu.example.clean.poke.data.resource.database.entity.PokeIndexCache
import com.nanoyatsu.example.clean.poke.data.resource.network.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.PokeIndexRepository
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.PokeNameImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class PokeIndexRepositoryImpl(
    private val networkResource: PokeNetworkResource,
    private val dao: PokeIndexDao,
    private val boundaryCallback: PokeIndexBoundaryCallback
) : PokeIndexRepository {
    override suspend fun list(): Listing<PokeNameImage> {
        val dataSourceFactory = dao.getPokeIndex().map { PokeNameImage.from(it) }
        val livePagedList =
            LivePagedListBuilder(dataSourceFactory, PokeIndexBoundaryCallback.PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        return Listing(
            pagedList = livePagedList,
            networkState = boundaryCallback.networkState,
            isRefreshing = boundaryCallback.isRefreshing,
            refresh = { refresh(boundaryCallback.networkState, boundaryCallback.isRefreshing) },
            retry = { boundaryCallback.retryAllFailed() }
        )
    }

    private fun refresh(networkState: LiveNetworkState, isRefreshing: LiveRefreshingState) {
        isRefreshing.postValue(true)
        CoroutineScope(context = Dispatchers.IO).launch {
            Timber.d("onRefresh")
            try {
                val response = networkResource.list(0, PokeIndexBoundaryCallback.PAGE_SIZE)
                val data = response.data?.pokemon_v2_pokemon?.map { PokeIndexCache.from(it) }
                    ?: throw Exception("data is missing. cannot refresh.")
//                    .results.map { PokeIndexCache.from(it) }
//                val response = networkResource.list(PokeNetworkResource.FETCH_POKEMONS_COUNT)
//                    .data?.pokemons?.map { PokeIndexCache.from(requireNotNull(it)) }
//                    ?: throw Exception("cannot get data on refresh.")

                // fixme 読み込み関数に「キャッシュ無視」オプションをつけるように変更
                // 削除だけでもpagingがいい感じにやってくれる（削除→０件表示→onZeroItemsLoaded）
                dao.deleteAllPokeIndex()
                dao.insertAllPokeIndex(data)
            } catch (e: Exception) {
                val message = e.message ?: "unknown error"
                networkState.postValue(NetworkState.Failed(message))
            } finally {
                isRefreshing.postValue(false)
            }
        }
    }
}