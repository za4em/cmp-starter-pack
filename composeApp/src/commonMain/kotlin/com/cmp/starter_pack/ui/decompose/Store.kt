package com.cmp.starter_pack.ui.decompose

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.cmp.starter_pack.common.result.Result
import com.cmp.starter_pack.common.result.onError
import com.cmp.starter_pack.common.store.Store
import com.cmp.starter_pack.common.utils.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun <Key : Any, Output : Any> Store<Key, Output>.collectAsState(
    key: Key?,
    refreshes: Flow<Boolean> = flowOf(true),
): Result<Output> {

    if (key == null) {
        return Result.empty()
    }

    val data: Output? by observeCacheNotNull(key).collectAsState(initial = null)
    var error: Throwable? by rememberSaveable(key) { mutableStateOf(null) }

    LaunchedEffect(key) {
        refreshes.collect { hardRefresh ->
            if (hardRefresh) error = null
            val fresh = getFresh(key).onError { logError(it) }
            error = fresh.error
        }
    }


    return Result(data, error)
}

@Composable
fun <Output : Any> Store<Unit, Output>.collectAsState(
    refreshes: Flow<Boolean> = flowOf(true),
) = collectAsState(key = Unit, refreshes)