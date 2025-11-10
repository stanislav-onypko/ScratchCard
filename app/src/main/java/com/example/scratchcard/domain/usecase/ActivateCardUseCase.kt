package com.example.scratchcard.domain.usecase

import com.example.scratchcard.domain.model.CardStatus
import com.example.scratchcard.domain.model.ScratchCard
import com.example.scratchcard.domain.repository.CardRepository
import javax.inject.Inject

class ActivateCardUseCase @Inject constructor(
    private val repository: CardRepository
) {
    suspend operator fun invoke(code: String): ScratchCard {
        val activationValue = repository.activateCode(code)
        val isActivated = activationValue.toIntOrNull()?.let { it > ACTIVATION_THRESHOLD } ?: false

        if (isActivated) {
            return ScratchCard(code = code, status = CardStatus.ACTIVATED)
        } else {
            throw IllegalStateException("The card could not be activated. Please try again.")
        }
    }

    companion object {
        private const val ACTIVATION_THRESHOLD = 277028
    }
}
