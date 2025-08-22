package com.cmp.starter_pack.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cmp.starter_pack.common.datasource.storage.createDataStore

actual class PlatformFactory() {

    actual val dataStore: DataStore<Preferences> by lazy {
        createDataStore()
    }
}