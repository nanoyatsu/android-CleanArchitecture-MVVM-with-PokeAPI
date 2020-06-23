package com.nanoyatsu.example.clean.poke.presentation.index

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.nanoyatsu.example.clean.poke.core.dataclass.Listing
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.domain.poke.GetPokeList
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import timber.log.Timber

class IndexViewModel(getPokeList: GetPokeList) : ViewModel() {
    private val mediatorPokeList = MediatorLiveData<PagedList<PokeNameImage>>()
    val pokeList: LiveData<PagedList<PokeNameImage>>
        get() = mediatorPokeList
    private val mediatorNetworkState = MediatorLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = mediatorNetworkState
    private val mediatorIsRefreshing = MediatorLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = mediatorIsRefreshing

    private var refresh: (() -> Unit)? = null
    private var retry: (() -> Unit)? = null

    fun refresh() = refresh?.invoke()
    fun retry() = retry?.invoke()

    private val getListOnResult: (Result<Listing<PokeNameImage>>) -> Unit = { result ->
        result
            .onSuccess { suc ->
                Timber.d("onSuccess")
                mediatorPokeList.addSource(suc.pagedList) { mediatorPokeList.value = it }
                mediatorNetworkState.addSource(suc.networkState) { mediatorNetworkState.value = it }
                mediatorIsRefreshing.addSource(suc.isRefreshing) { mediatorIsRefreshing.value = it }
                refresh = suc.refresh
                retry = suc.retry
            }
            .onFailure {
                Timber.d("onFailure")
            }
    }

    init {
        Timber.d("IndexViewModel init")
        getPokeList(Unit, getListOnResult)
    }
}
