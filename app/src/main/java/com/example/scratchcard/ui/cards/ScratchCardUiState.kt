package com.example.scratchcard.ui.cards

import com.example.scratchcard.domain.model.CardStatus

sealed class ScratchCardUiState(
    open val code: String? = null,
    open val status: CardStatus = CardStatus.NOT_SCRATCHED
) {
    data class Content(
        override val code: String?,
        override val status: CardStatus
    ) : ScratchCardUiState(code, status)

    data class Loading(
        override val code: String?,
        override val status: CardStatus
    ) : ScratchCardUiState(code, status)
}
