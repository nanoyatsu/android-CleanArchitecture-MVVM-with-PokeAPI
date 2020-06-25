package com.nanoyatsu.example.clean.poke.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.GetPoke
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.PokeDetail
import timber.log.Timber

class DetailViewModel(args: DetailFragmentArgs, getPoke: GetPoke) : ViewModel() {
    private val number = args.number

    private val _poke = MutableLiveData<PokeDetail>()
    val poke: LiveData<PokeDetail> = _poke

    val sprite = Transformations.map(poke) { it.spriteFrontDefault }
    val name = Transformations.map(poke) { it.name }
    val height = Transformations.map(poke) { it.height.toString() }
    val weight = Transformations.map(poke) { it.weight.toString() }

    private val getOnResult: (Result<PokeDetail>) -> Unit = { result ->
        result
            .onSuccess {
                Timber.d("onSuccess")
                _poke.value = it
            }
            .onFailure {
                Timber.d("onFailure")
            }
    }

    init {
        getPoke(number, getOnResult)
    }
}
