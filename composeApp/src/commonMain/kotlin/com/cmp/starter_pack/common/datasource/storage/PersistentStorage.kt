package com.cmp.starter_pack.common.datasource.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.cmp.starter_pack.common.result.catching
import com.cmp.starter_pack.common.result.onError
import com.cmp.starter_pack.common.utils.logError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

/**
 *   Gets the singleton DataStore instance, creating it if necessary.
 */
fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val dataStoreFileName = "app.preferences_pb"

abstract class PersistentStorage<T : @Serializable Any>(
    private val dataStore: DataStore<Preferences>,
    private val storageKey: String,
    private val serializer: KSerializer<T>,
    private val defaultValue: T?,
) : Storage<T> {

    private val scope = CoroutineScope(Dispatchers.IO)


    val key = stringPreferencesKey(storageKey)

    init {
        scope.launch {
            val currentValue = valueOrNull()
            if (currentValue == null && defaultValue != null) {
                update(defaultValue)
            }
        }
    }

    override fun flow(): Flow<T?> {
        return dataStore.data
            .mapNotNull { preferences -> preferences.getEncodedValue() }
            .distinctUntilChanged()
            .mapNotNull { value -> value.decodeValue() }
    }

    override suspend fun valueOrNull(): T? {
        return dataStore.data
            .firstOrNull()
            ?.getEncodedValue()
            ?.decodeValue()
    }

    override suspend fun update(newValue: T?) {
        catching {
            dataStore.edit { preferences ->
                if (newValue != null) {
                    val encoded = newValue.encodeValue()
                    if (encoded != null) {
                        preferences[key] = encoded
                    }
                } else {
                    preferences.remove(key)
                }
            }
        }.onError {
            logError(it)
        }
    }

    override suspend fun resetToDefault() {
        update(defaultValue)
    }

    private fun Preferences.getEncodedValue(): String? {
        return this[key]
    }

    private fun String.decodeValue(): T? {
        return catching {
            Json.decodeFromString(serializer, this)
        }
            .onError { logError(it) }
            .data
    }

    private fun T.encodeValue(): String? {
        return catching {
            Json.encodeToString(serializer, this)
        }
            .onError { logError(it) }
            .data
    }
}