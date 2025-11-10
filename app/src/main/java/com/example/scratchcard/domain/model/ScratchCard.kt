package com.example.scratchcard.domain.model

data class ScratchCard(
    val code: String? = null,
    val status: CardStatus = CardStatus.NOT_SCRATCHED
)