package com.nanoyatsu.example.clean.poke.data.repository

import androidx.paging.LivePagedListBuilder
import com.nanoyatsu.example.clean.poke.core.dataclass.Listing
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveNetworkState
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveRefreshingState
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.data.database.dao.PokeDao
import com.nanoyatsu.example.clean.poke.data.database.entity.PokeIndexCache
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility
import com.nanoyatsu.example.clean.poke.data.resource.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.domain.poke.PokeDetail
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import com.nanoyatsu.example.clean.poke.domain.poke.PokeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class PokeRepositoryImpl(
    private val networkResource: PokeNetworkResource,
    private val dao: PokeDao,
    private val boundaryCallback: PokeIndexBoundaryCallback
) : PokeRepository {
    override suspend fun get(id: Int): PokeDetail {
        val dbModel = dao.getPoke(id)
            ?: fromNetworkWithCaching(id, networkResource, dao)
        return PokeDetail.from(dbModel)
    }

    private suspend fun fromNetworkWithCaching(
        id: Int, api: PokeNetworkResource, dao: PokeDao
    ): PokeCacheWithTypeAndAbility {
        val convertedApiResult = api.get(id)
            .let(PokeCacheWithTypeAndAbility.Companion::from)
        insertPokeCacheWithTypeAndAbility(convertedApiResult, dao)
        return convertedApiResult
    }

    private suspend fun insertPokeCacheWithTypeAndAbility(
        row: PokeCacheWithTypeAndAbility, dao: PokeDao
    ) {
        dao.insertPoke(row.poke)
        row.types.map { it.type }.let { dao.insertAllTypeIndex(it) }
        row.types.map { it.pokeType }.let { dao.insertAllPokeType(it) }
        row.abilities.map { it.ability }.let { dao.insertAllAbilityIndex(it) }
        row.abilities.map { it.pokeAbility }.let { dao.insertAllPokeAbility(it) }
    }

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
                    .results.map { PokeIndexCache.from(it) }
                if (response.isEmpty())
                    throw Exception("cannot get data on refresh.")

                // 削除だけでもpagingがいい感じにやってくれる（削除→０件表示→onZeroItemsLoaded）
                dao.deleteAllPokeIndex()
                dao.insertAllPokeIndex(response)
            } catch (e: Exception) {
                val message = e.message ?: "unknown error"
                networkState.postValue(NetworkState.Failed(message))
            } finally {
                isRefreshing.postValue(false)
            }
        }
    }
}