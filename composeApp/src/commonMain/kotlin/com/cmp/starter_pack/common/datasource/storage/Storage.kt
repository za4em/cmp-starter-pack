package com.cmp.starter_pack.common.datasource.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface Storage<T: @Serializable Any> {
    fun flow(): Flow<T?>

    suspend fun valueOrNull(): T?

    suspend fun update(newValue: T?)

    suspend fun resetToDefault()
}
