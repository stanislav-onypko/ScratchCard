package com.example.scratchcard.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scratchcard.R
import com.example.scratchcard.ui.cards.ScratchCardUiState
import com.example.scratchcard.ui.cards.ScratchCardViewModel

@Composable
fun ScratchScreen(navController: NavController, viewModel: ScratchCardViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            val activity = context as? Activity
            if (activity?.isChangingConfigurations == false) {
                viewModel.cancelScratch()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        uiState.code?.let {
            Text(text = stringResource(id = R.string.code_label, it))
            Spacer(modifier = Modifier.height(8.dp))
        }

        when (uiState) {
            is ScratchCardUiState.Loading -> CircularProgressIndicator()
            else -> {
                Button(onClick = { viewModel.scratchCard() }) {
                    Text(stringResource(id = R.string.scratch_card_button))
                }
            }
        }
    }
}
