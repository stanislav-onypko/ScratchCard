package com.example.scratchcard.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.scratchcard.R
import com.example.scratchcard.ui.cards.ScratchCardViewModel
import com.example.scratchcard.ui.screens.ActivationScreen
import com.example.scratchcard.ui.screens.MainScreen
import com.example.scratchcard.ui.screens.ScratchScreen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ScratchCardViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentScreenTitle = when (currentRoute) {
        "scratch" -> stringResource(id = R.string.scratch_screen_title)
        "activation" -> stringResource(id = R.string.activation_screen_title)
        else -> stringResource(id = R.string.main_screen_title)
    }

    LaunchedEffect(Unit) {
        viewModel.errorEvent.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(currentScreenTitle) })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = Color.Red,
                    snackbarData = data
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
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
}
