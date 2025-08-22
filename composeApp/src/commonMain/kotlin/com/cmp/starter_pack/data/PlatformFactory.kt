package com.cmp.starter_pack.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect class PlatformFactory {
    val dataStore: DataStore<Preferences>
}