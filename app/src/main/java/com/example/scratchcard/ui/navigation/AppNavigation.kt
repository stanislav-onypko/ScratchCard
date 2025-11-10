package com.example.scratchcard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scratchcard.ui.cards.ScratchCardViewModel
import com.example.scratchcard.ui.screens.ActivationScreen
import com.example.scratchcard.ui.screens.MainScreen
import com.example.scratchcard.ui.screens.ScratchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ScratchCardViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable("scratch") {
            ScratchScreen(navController = navController, viewModel = viewModel)
        }
        composable("activation") {
            ActivationScreen(navController = navController, viewModel = viewModel)
        }
    }
}
