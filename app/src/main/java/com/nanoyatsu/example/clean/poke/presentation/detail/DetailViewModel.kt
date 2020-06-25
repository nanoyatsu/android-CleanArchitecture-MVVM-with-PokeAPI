package com.nanoyatsu.example.clean.poke.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
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
    private val number = args.number

    private val _poke = MutableLiveData<PokeDetail>()
    val poke: LiveData<PokeDetail> = _poke

    val sprite = Transformations.map(poke) { it.spriteFrontDefault }
    val name = Transformations.map(poke) { it.name }
    val height = Transformations.map(poke) { it.height.toString() }
    val weight = Transformations.map(poke) { it.weight.toString() }

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val setPokeDetail: (Result<PokeDetail>) -> Unit = { result ->
        result
            .onSuccess {
                Timber.d("onSuccess")
                _poke.value = it
                _isRefreshing.value = false
            }
            .onFailure {
                Timber.d("onFailure")
                _isRefreshing.value = false
            }
    }

    init {
        _isRefreshing.value = true
        getPokeDetail(number, setPokeDetail)
    }

    fun doRefresh() {
        _isRefreshing.value = true
        refreshPokeDetail(number, setPokeDetail)
    }
}
