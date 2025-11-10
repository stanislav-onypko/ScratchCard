package com.example.scratchcard.data.mapper

import com.example.scratchcard.data.remote.dto.ActivateResponse
import com.example.scratchcard.domain.model.ScratchCard
import com.example.scratchcard.domain.model.CardStatus

fun ActivateResponse.toScratchCard(code: String): ScratchCard {
    val isActivated = android.toIntOrNull()?.let { it > 277028 } ?: false
    return ScratchCard(
        code = code,
        status = if (isActivated) CardStatus.ACTIVATED else CardStatus.SCRATCHED
    )
}
