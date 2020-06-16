package com.nanoyatsu.example.clean.poke.core.di

import com.nanoyatsu.example.clean.poke.data.resource.PokeDataSource
import me.sargunvohra.lib.pokekotlin.client.PokeApi
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import org.koin.dsl.module

val appModule = module {
    // common
    single<PokeApi> { PokeApiClient() }

    // data
    single { PokeDataSource(get()) }
}