package com.nanoyatsu.example.clean.poke.core.dataclass

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * https://github.com/android/architecture-components-samples/tree/master/PagingWithNetworkSample を参考
 * androidx.paging.xxDataSource から通信状態系の情報も受け取る時にまとめるためのデータクラス
 */
data class Listing<T>(
    // UIの表示対象リスト
    val pagedList: LiveData<PagedList<T>>,
    // 通信ステータス
    val networkState: LiveNetworkState,
    // 再通信操作時の表示(swipe-refreshに使う)
    val isRefreshing: LiveRefreshingState,
    // リスト全体の再取得処理
    val refresh: () -> Unit,
    // エラー時の再試行処理
    val retry: () -> Unit
)