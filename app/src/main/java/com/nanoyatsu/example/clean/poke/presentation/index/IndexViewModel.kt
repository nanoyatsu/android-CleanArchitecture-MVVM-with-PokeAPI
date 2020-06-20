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

    private val getListOnResult: (Result<List<PokeNameImage>>) -> Unit = { result ->
        result
            .onSuccess {
                Timber.d("onSuccess")
                _list.value = it
            }
            .onFailure {
                Timber.d("onFailure")
            }
    }

    init {
        Timber.d("IndexViewModel init")
        getPokeList(Pagination(0, 30), getListOnResult)
    }

}
