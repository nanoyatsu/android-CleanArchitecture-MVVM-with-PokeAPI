package com.nanoyatsu.example.clean.poke.presentation.index

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nanoyatsu.example.clean.poke.core.dataclass.Pagination
import com.nanoyatsu.example.clean.poke.domain.poke.GetPokeList
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import timber.log.Timber

class IndexViewModel(val getPokeList: GetPokeList) : ViewModel() {
    private val _list =
        MutableLiveData<List<NamedApiResource>>().apply {}
    val list: LiveData<List<NamedApiResource>> = _list

    init {
        Timber.d("IndexViewModel init")
        _list.postValue(listOf(NamedApiResource("name", "category", 1)))
        getPokeList(Pagination(0, 30), this::getListOnResult)
    }

    private fun getListOnResult(result: Result<List<NamedApiResource>>) {
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
