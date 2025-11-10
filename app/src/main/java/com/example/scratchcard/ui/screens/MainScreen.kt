package com.example.scratchcard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scratchcard.R
import com.example.scratchcard.ui.cards.ScratchCardViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: ScratchCardViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.card_state_label, uiState.status.name))
        uiState.code?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.code_label, it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("scratch") }) {
            Text(stringResource(id = R.string.go_to_scratch_screen_button))
        }
        Button(onClick = { navController.navigate("activation") }) {
            Text(stringResource(id = R.string.go_to_activation_screen_button))
        }
    }
}
