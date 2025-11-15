package com.cmp.starter_pack.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cmp.starter_pack.common.designsystem.controllers.AlertController
import com.cmp.starter_pack.data.storage.UserStorage

class DataContainer(
    private val dataStore: DataStore<Preferences>,
) {
    val alertController: AlertController = AlertController()

    val userStorage: UserStorage by lazy {
        UserStorage(dataStore)
    }

}