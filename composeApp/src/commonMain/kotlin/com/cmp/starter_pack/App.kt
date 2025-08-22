package com.cmp.starter_pack

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import cmp_starter_pack.composeapp.generated.resources.Res
import cmp_starter_pack.composeapp.generated.resources.compose_multiplatform
import com.cmp.starter_pack.data.DataContainer
import com.cmp.starter_pack.data.PlatformFactory
import com.cmp.starter_pack.data.model.User
import kotlinx.coroutines.launch

@Composable
@Preview
fun App(platformFactory: PlatformFactory) {
    val dataContainer = remember { DataContainer(platformFactory.dataStore) }
    val coroutineScope = rememberCoroutineScope()
    val currentUser by dataContainer.userStorage.flow().collectAsState(null)

    MaterialTheme {
        var showContent by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        val clickedUpdated = (currentUser?.clickedTimes ?: 0) + 1
                        val userUpdated = currentUser?.copy(clickedTimes = clickedUpdated)
                            ?: User(0, "Test", clickedUpdated)
                        dataContainer.userStorage.update(userUpdated)
                    }
                },
            ) {
                Text("Click me, ${currentUser?.name ?: "Anon"}!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Clicked: ${currentUser?.clickedTimes ?: 0}")
                }
            }
        }
    }
}