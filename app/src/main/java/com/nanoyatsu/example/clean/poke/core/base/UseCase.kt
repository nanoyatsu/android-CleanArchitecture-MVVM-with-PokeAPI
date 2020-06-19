package com.nanoyatsu.example.clean.poke.core.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class UseCase<in Params, out Return> where Return : Any {

    abstract suspend fun run(params: Params): Return

    operator fun invoke(params: Params, onResult: (Result<Return>) -> Unit) {
        val job = CoroutineScope(Dispatchers.IO)
            .async { kotlin.runCatching { run(params) } }
        CoroutineScope(Dispatchers.Main).launch { onResult(job.await()) }
    }
}