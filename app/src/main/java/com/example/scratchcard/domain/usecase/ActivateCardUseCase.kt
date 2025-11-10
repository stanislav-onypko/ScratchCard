package com.example.scratchcard.domain.usecase

import com.example.scratchcard.domain.model.ScratchCard
import com.example.scratchcard.domain.repository.CardRepository
import javax.inject.Inject

class ActivateCardUseCase @Inject constructor(
    private val repository: CardRepository
) {
    suspend operator fun invoke(code: String): ScratchCard {
        return repository.activateCode(code)
    }
}
