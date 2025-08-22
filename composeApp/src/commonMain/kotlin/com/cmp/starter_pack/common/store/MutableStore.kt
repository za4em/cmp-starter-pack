package com.cmp.starter_pack.common.store

import com.cmp.starter_pack.common.result.Result
import com.cmp.starter_pack.common.result.catching
import com.cmp.starter_pack.common.result.onSuccess
import com.cmp.starter_pack.common.store.cache.InMemoryCache
import com.cmp.starter_pack.common.store.cache.StoreCache
import com.cmp.starter_pack.common.store.network.StoreNetwork
import com.cmp.starter_pack.common.store.network.StoreUpdater

open class MutableStore<KEY, DATA, UPDATE>(
    network: StoreNetwork<KEY, DATA>,
    private val networkErrorConverter: Throwable.() -> Throwable = { this },
    private val cache: StoreCache<KEY, DATA> = InMemoryCache(),
    private val updater: StoreUpdater<KEY, UPDATE, DATA>,
) : Store<KEY, DATA>(network, networkErrorConverter, cache) {

    suspend fun update(key: KEY, update: UPDATE): Result<DATA> {
        return catching(networkErrorConverter) { updater(key, update) }.onSuccess {
            cache.set(
                key,
                it
            )
        }
    }
}
