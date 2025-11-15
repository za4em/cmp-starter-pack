package com.cmp.starter_pack.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cmp.starter_pack.common.datasource.network.httpClient
import com.cmp.starter_pack.common.designsystem.controllers.AlertController
import com.cmp.starter_pack.data.storage.UserStorage

class DataContainer(
    private val dataStore: DataStore<Preferences>,
    private val appConfig: AppConfig,
) {
    val alertController: AlertController = AlertController()
    val httpClient = httpClient(appConfig.baseUrl)

    val userStorage: UserStorage by lazy {
        UserStorage(dataStore)
    }

}