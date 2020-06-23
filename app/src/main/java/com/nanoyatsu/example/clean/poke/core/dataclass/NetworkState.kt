package com.nanoyatsu.example.clean.poke.core.dataclass

import androidx.lifecycle.MutableLiveData

sealed class NetworkState {
    object Success : NetworkState()
    object Running : NetworkState()
    data class Failed(val msg: String?) : NetworkState()
}

// MutableLiveData<>をKoinのModuleにそのまま複数入れるとorg.koin.core.error.DefinitionOverrideException
class LiveNetworkState(state: NetworkState) : MutableLiveData<NetworkState>(state)
class LiveRefreshingState(isRefreshing: Boolean) : MutableLiveData<Boolean>(isRefreshing)