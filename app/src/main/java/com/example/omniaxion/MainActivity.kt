package com.example.omniaxion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.omniaxion.presentation.navigation.NavGraph
import com.example.omniaxion.ui.theme.OmniAxionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OmniAxionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavGraph()
                }
            }
        }
    }
}
