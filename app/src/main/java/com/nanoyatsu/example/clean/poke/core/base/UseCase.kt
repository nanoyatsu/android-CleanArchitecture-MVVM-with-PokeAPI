package com.nanoyatsu.example.clean.poke.core.base

import kotlinx.coroutines.*

/**
 * UseCase部の基底クラス
 * [run]を定義することで、利用側では`クラス名()`の形でデータ呼び出しが可能
 */
abstract class UseCase<in Params, out Return> where Return : Any {

    /**
     * どのCoroutineContext利用するかは実装箇所で指定する。
     * UseCaseクラスを利用する場面では、大抵の場合IOを指定する見込み。
     *
     * Dispatches.Main : メインスレッド。UseCaseとしてはほぼ使われない見込み。UIとのやりとり、LiveDataの更新など。
     * Dispatches.IO : メインスレッド外。ディスクやネットワークIOに最適化されている。データベース、ファイルのRead-Write、ネットワークアクセスなど。
     * Dispatches.Default : メインスレッド外。CPUに負荷がかかる処理に最適化されている。サイズの大きなデータ処理など。
     */
    abstract suspend fun run(params: Params): Return

    /**
     * @param scope 呼び出し側で管理する[CoroutineScope]。viewModelからの呼び出しで、画面と同じ生存期間であれば`viewModelScope`を渡すとよい。
     * @param params jobの引数。とくに無い場合はUnitでよい。
     * @param onResult job完了後の処理。メインスレッドで実行される。
     */
    operator fun invoke(scope: CoroutineScope, params: Params, onResult: (Result<Return>) -> Unit) {
        val job = scope.async { kotlin.runCatching { run(params) } }
        scope.launch {
            withContext(Dispatchers.Main) { onResult(job.await()) }
        }
    }
}