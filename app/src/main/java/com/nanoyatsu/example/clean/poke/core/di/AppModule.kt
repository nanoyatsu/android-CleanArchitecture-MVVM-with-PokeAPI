package com.nanoyatsu.example.clean.poke.core.di

import android.app.Activity
import android.view.ViewGroup
import com.nanoyatsu.example.clean.poke.data.repository.PokeRepositoryImpl
import com.nanoyatsu.example.clean.poke.data.resource.PokeDataSource
import com.nanoyatsu.example.clean.poke.domain.poke.GetPokeList
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import com.nanoyatsu.example.clean.poke.domain.poke.PokeRepository
import com.nanoyatsu.example.clean.poke.presentation.detail.DetailViewModel
import com.nanoyatsu.example.clean.poke.presentation.index.IndexAdapter
import com.nanoyatsu.example.clean.poke.presentation.index.IndexFragment
import com.nanoyatsu.example.clean.poke.presentation.index.IndexItemViewHolder
import com.nanoyatsu.example.clean.poke.presentation.index.IndexViewModel
import com.nanoyatsu.example.clean.poke.presentation.main.MainActivity
import me.sargunvohra.lib.pokekotlin.client.PokeApi
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // common
    single<PokeApi> { PokeApiClient() }

    // data
    single { PokeDataSource(get()) }

    // repository
    single<PokeRepository> { PokeRepositoryImpl(get()) }

    // useCase
    single { GetPokeList(get()) }

    // viewModel
    viewModel { IndexViewModel(get()) }
    viewModel { DetailViewModel() }

    // UI
//    // スコープ理解不足
//    scope<MainActivity> {
//        scoped<IndexItemViewHolder.Navigation>
//        { (activity: Activity) -> IndexFragment.Navigation(activity) }
//        scoped<IndexAdapter>
//        { (list: List<PokeNameImage>) -> IndexAdapter(list, get()) } // たぶんlistもget()できるようになる
//        scoped<IndexItemViewHolder>
//        { (vg: ViewGroup) -> IndexItemViewHolder.from(vg, get()) }
//    }
}