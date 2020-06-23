package com.nanoyatsu.example.clean.poke.core.di

import com.nanoyatsu.example.clean.poke.core.dataclass.LiveNetworkState
import com.nanoyatsu.example.clean.poke.core.dataclass.LiveRefreshingState
import com.nanoyatsu.example.clean.poke.core.dataclass.NetworkState
import com.nanoyatsu.example.clean.poke.data.database.PokeDataBase
import com.nanoyatsu.example.clean.poke.data.repository.PokeIndexBoundaryCallback
import com.nanoyatsu.example.clean.poke.data.repository.PokeRepositoryImpl
import com.nanoyatsu.example.clean.poke.data.resource.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.domain.poke.GetPoke
import com.nanoyatsu.example.clean.poke.domain.poke.GetPokeList
import com.nanoyatsu.example.clean.poke.domain.poke.PokeRepository
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
    single { get<PokeDataBase>().pokeDao() }
    single { PokeNetworkResource(get()) }

    // repository
    single<PokeRepository> { PokeRepositoryImpl(get(), get()) }
    single { PokeIndexBoundaryCallback(get(), get(), get(), get()) }

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