package com.nanoyatsu.example.clean.poke.presentation.index

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nanoyatsu.example.clean.poke.core.dataclass.Pagination
import com.nanoyatsu.example.clean.poke.domain.poke.GetPokeList
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import timber.log.Timber

class IndexViewModel(val getPokeList: GetPokeList) : ViewModel() {
    private val _list = MutableLiveData<List<PokeNameImage>>()
    val list: LiveData<List<PokeNameImage>> = _list

    init {
        Timber.d("IndexViewModel init")
        getPokeList(Pagination(0, 30), this::getListOnResult)
    }

    private fun getListOnResult(result: Result<List<PokeNameImage>>) {
        result
            .onSuccess {
                Timber.d("onSuccess")
                _list.postValue(it)
            }
            .onFailure {
                Timber.d("onFailure")
            }
    }
}
