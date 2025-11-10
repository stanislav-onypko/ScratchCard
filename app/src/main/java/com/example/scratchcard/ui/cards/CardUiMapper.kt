package com.example.scratchcard.ui.cards

import com.example.scratchcard.domain.model.ScratchCard

fun ScratchCard.toUiState(): ScratchCardUiState.Content {
    return ScratchCardUiState.Content(
        code = this.code,
        status = this.status
    )
}
