package com.nanoyatsu.example.clean.poke.core.di

import com.nanoyatsu.example.clean.poke.core.dataclass.LiveNetworkState
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveRefreshingState
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.data.database.PokeDataBase
import com.nanoyatsu.example.clean.poke.data.repository.PokeDetailRepositoryImpl
import com.nanoyatsu.example.clean.poke.data.repository.PokeIndexBoundaryCallback
import com.nanoyatsu.example.clean.poke.data.repository.PokeIndexRepositoryImpl
import com.nanoyatsu.example.clean.poke.data.resource.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.GetPoke
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.PokeDetailRepository
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.GetPokeList
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.PokeIndexRepository
import com.nanoyatsu.example.clean.poke.presentation.detail.DetailFragmentArgs
import com.nanoyatsu.example.clean.poke.presentation.detail.DetailViewModel
import com.nanoyatsu.example.clean.poke.presentation.index.IndexViewModel
import me.sargunvohra.lib.pokekotlin.client.PokeApi
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // common
    single<PokeApi> { PokeApiClient() }
    factory { LiveNetworkState(NetworkState.Success) }
    factory { LiveRefreshingState(false) }

    // data
    single { PokeDataBase.getInstance(androidApplication().applicationContext) }
    single { get<PokeDataBase>().pokeIndexDao() }
    single { get<PokeDataBase>().pokeDetailDao() }
    single { PokeNetworkResource(get()) }

    // repository
    single { PokeIndexBoundaryCallback(get(), get(), get(), get()) }
    single<PokeIndexRepository> { PokeIndexRepositoryImpl(get(), get(), get()) }
    single<PokeDetailRepository> { PokeDetailRepositoryImpl(get(), get()) }

    // useCase
    single { GetPokeList(get()) }
    single { GetPoke(get()) }

    // viewModel
    viewModel { IndexViewModel(get()) }
    viewModel { (args: DetailFragmentArgs) -> DetailViewModel(args, get()) }

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