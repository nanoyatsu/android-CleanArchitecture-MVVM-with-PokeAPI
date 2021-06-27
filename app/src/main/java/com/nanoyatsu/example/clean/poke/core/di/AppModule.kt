package com.nanoyatsu.example.clean.poke.core.di

import com.nanoyatsu.example.clean.poke.core.dataclass.LiveNetworkState
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveRefreshingState
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.data.repository.PokeDetailRepositoryImpl
import com.nanoyatsu.example.clean.poke.data.repository.PokeIndexBoundaryCallback
import com.nanoyatsu.example.clean.poke.data.repository.PokeIndexRepositoryImpl
import com.nanoyatsu.example.clean.poke.data.resource.database.PokeDataBase
import com.nanoyatsu.example.clean.poke.data.resource.network.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.data.resource.network.client.GraphQlPokemonClient
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.GetPokeDetail
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.PokeDetailRepository
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.RefreshPokeDetail
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.GetPokeList
import com.nanoyatsu.example.clean.poke.domain.pokeIndex.PokeIndexRepository
import com.nanoyatsu.example.clean.poke.presentation.detail.DetailFragmentArgs
import com.nanoyatsu.example.clean.poke.presentation.detail.DetailViewModel
import com.nanoyatsu.example.clean.poke.presentation.index.IndexViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val appModule = module {
    // common
    single(qualifier<GraphQlPokemonClient>()) { GraphQlPokemonClient.get() }
    factory { LiveNetworkState(NetworkState.Success) }
    factory { LiveRefreshingState(false) }

    // data
    single { PokeDataBase.getInstance(androidApplication().applicationContext) }
    single { get<PokeDataBase>().pokeIndexDao() }
    single { get<PokeDataBase>().pokeDetailDao() }
    single { PokeNetworkResource(get(qualifier<GraphQlPokemonClient>())) }

    // repository
    single { PokeIndexBoundaryCallback(get(), get(), get(), get()) }
    single<PokeIndexRepository> { PokeIndexRepositoryImpl(get(), get(), get()) }
    single<PokeDetailRepository> { PokeDetailRepositoryImpl(get(), get(), get()) }

    // useCase
    single { GetPokeList(get()) }
    single { GetPokeDetail(get()) }
    single { RefreshPokeDetail(get()) }

    // viewModel
    viewModel { IndexViewModel(get()) }
    viewModel { (args: DetailFragmentArgs) -> DetailViewModel(args, get(), get()) }

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