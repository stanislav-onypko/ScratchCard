package com.example.scratchcard.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scratchcard.domain.generator.CodeGenerator
import com.example.scratchcard.domain.model.CardStatus
import com.example.scratchcard.domain.usecase.ActivateCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScratchCardViewModel @Inject constructor(
    private val activateCardUseCase: ActivateCardUseCase,
    private val codeGenerator: CodeGenerator
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScratchCardUiState>(
        ScratchCardUiState.Content(code = null, status = CardStatus.NOT_SCRATCHED)
    )
    val uiState: StateFlow<ScratchCardUiState> = _uiState.asStateFlow()

    private var scratchJob: Job? = null

    fun scratchCard() {
        scratchJob?.cancel()
        scratchJob = viewModelScope.launch {
            _uiState.value = ScratchCardUiState.Loading(
                _uiState.value.code, _uiState.value.status
            )
            delay(2000) // Simulate long-running operation
            val code = codeGenerator.generate()
            _uiState.value = ScratchCardUiState.Content(
                code = code,
                status = CardStatus.SCRATCHED
            )
        }
    }

    fun cancelScratch() {
        scratchJob?.cancel()
        _uiState.value = ScratchCardUiState.Content(
            _uiState.value.code, _uiState.value.status
        )
    }

    fun activateCard() {
        val code = _uiState.value.code ?: return
        viewModelScope.launch {
            _uiState.value = ScratchCardUiState.Loading(
                _uiState.value.code, _uiState.value.status
            )
            try {
                val scratchCard = activateCardUseCase(code)
                _uiState.value = scratchCard.toUiState()
            } catch (e: Exception) {
                _uiState.value = ScratchCardUiState.Error(
                    _uiState.value.code,
                    _uiState.value.status,
                    "Activation failed: ${e.message}"
                )
            }
        }
    }
}
