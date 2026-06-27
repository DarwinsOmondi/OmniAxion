package com.example.omniaxion.presentation.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.omniaxion.domain.model.NewsArticle
import com.example.omniaxion.presentation.auth.AuthScreen
import com.example.omniaxion.presentation.auth.AuthViewModel
import com.example.omniaxion.presentation.dashboard.DashboardScreen
import com.example.omniaxion.presentation.dashboard.DashboardViewModel
import com.example.omniaxion.presentation.atlas.AtlasScreen
import com.example.omniaxion.presentation.timeline.TimelineScreen
import com.example.omniaxion.presentation.payment.PaymentScreen

@Composable
fun NavGraph(
    startDestination: String = "dashboard"
) {
    val navController = rememberNavController()
    var selectedArticle by remember { mutableStateOf<NewsArticle?>(null) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("auth") {
            val viewModel: AuthViewModel = hiltViewModel()
            AuthScreen(
                viewModel = viewModel,
                onAuthSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }

        composable("dashboard") {
            val viewModel: DashboardViewModel = hiltViewModel()
            DashboardScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    selectedArticle = article
                    navController.navigate("timeline")
                },
                onMapClick = {
                    navController.navigate("atlas")
                }
            )
        }

        composable("timeline") {
            TimelineScreen(
                title = selectedArticle?.title ?: "Silicon Valley Bank Crisis",
                articles = selectedArticle?.let { listOf(it) } ?: emptyList(),
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("atlas") {
            AtlasScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable("payment") {
            PaymentScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
