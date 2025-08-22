package com.cmp.starter_pack.common.datasource.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

abstract class InMemoryStorage<T : @Serializable Any>(val defaultValue: T? = null) : Storage<T> {

    private val mutableStateFlow: MutableStateFlow<T?> = MutableStateFlow(defaultValue)

    override fun flow(): Flow<T?> {
        return mutableStateFlow.asStateFlow()
    }

    override suspend fun update(newValue: T?) {
        mutableStateFlow.emit(newValue)
    }

    override suspend fun valueOrNull(): T? {
        return mutableStateFlow.value
    }

    override suspend fun resetToDefault() {
        mutableStateFlow.emit(defaultValue)
    }
}
