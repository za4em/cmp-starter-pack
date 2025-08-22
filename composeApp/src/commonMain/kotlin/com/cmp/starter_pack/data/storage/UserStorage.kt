package com.cmp.starter_pack.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cmp.starter_pack.common.datasource.storage.PersistentStorage
import com.cmp.starter_pack.data.model.User

class UserStorage(dataStore: DataStore<Preferences>): PersistentStorage<User>(
    dataStore = dataStore,
    storageKey = "user",
    serializer = User.serializer(),
    defaultValue = null,
)