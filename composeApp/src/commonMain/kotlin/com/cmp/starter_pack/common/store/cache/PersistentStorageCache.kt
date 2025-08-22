package com.cmp.starter_pack.common.store.cache

import com.cmp.starter_pack.common.datasource.storage.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

open class PersistentStorageCache<PARAMS, DATA : @Serializable Any>(
    private val storage: Storage<DATA>,
) : StoreCache<PARAMS, DATA> {

    override fun observe(params: PARAMS): Flow<DATA?> {
        return storage.flow()
    }

    override suspend fun get(params: PARAMS): DATA? {
        return storage.valueOrNull()
    }

    override suspend fun set(params: PARAMS, data: DATA) {
        storage.update(data)
    }

    override suspend fun clear(params: PARAMS) {
        storage.resetToDefault()
    }

    override suspend fun clearAll() {
        storage.resetToDefault()
    }
}
