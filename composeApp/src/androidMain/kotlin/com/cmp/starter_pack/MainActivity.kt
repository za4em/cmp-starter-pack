package com.cmp.starter_pack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cmp.starter_pack.data.PlatformFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val platformFactory = PlatformFactory(context = this)

        setContent {
            App(platformFactory)
        }
    }
}