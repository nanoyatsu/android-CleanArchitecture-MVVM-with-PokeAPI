package com.nanoyatsu.example.clean.poke.presentation.detail

import androidx.lifecycle.*
import com.nanoyatsu.example.clean.poke.core.extension.proper
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.GetPokeDetail
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.PokeDetail
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.RefreshPokeDetail
import timber.log.Timber

class DetailViewModel(
    args: DetailFragmentArgs,
    getPokeDetail: GetPokeDetail,
    val refreshPokeDetail: RefreshPokeDetail
) :
    ViewModel() {
    val number = args.number

    private val _poke = MutableLiveData<PokeDetail>()
    val poke: LiveData<PokeDetail> = _poke

    val sprite = Transformations.map(poke) { it.spriteFrontDefault }
    val name = Transformations.map(poke) { it.name.proper() }
    val height = Transformations.map(poke) { it.height.toString() }
    val weight = Transformations.map(poke) { it.weight.toString() }

    // レイアウト作ってないのでタイプ・とくせいの表示してません
    val types = Transformations.map(poke) { it.types }
    val abilities = Transformations.map(poke) { it.abilities }
    val statH = Transformations.map(poke) { it.statH.toString() }
    val statA = Transformations.map(poke) { it.statA.toString() }
    val statB = Transformations.map(poke) { it.statB.toString() }
    val statC = Transformations.map(poke) { it.statC.toString() }
    val statD = Transformations.map(poke) { it.statD.toString() }
    val statS = Transformations.map(poke) { it.statS.toString() }

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _simpleMessage = MutableLiveData<String>("")
    val simpleMessage: LiveData<String>
        get() = _simpleMessage

    private val setPokeDetail: (Result<PokeDetail>) -> Unit = { result ->
        result
            .onSuccess {
                Timber.d("onSuccess")
                _poke.value = it
                _isRefreshing.value = false
            }
            .onFailure {
                Timber.d("onFailure")
                _simpleMessage.value = it.message
                _isRefreshing.value = false
            }
    }

    init {
        _isRefreshing.value = true
        getPokeDetail(viewModelScope, number, setPokeDetail)
    }

    fun doRefresh() {
        _isRefreshing.value = true
        refreshPokeDetail(viewModelScope, number, setPokeDetail)
    }
}
