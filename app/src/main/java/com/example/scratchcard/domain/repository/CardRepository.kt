package com.example.scratchcard.domain.repository

import com.example.scratchcard.domain.model.ScratchCard

interface CardRepository {
    suspend fun activateCode(code: String): ScratchCard
}