package com.example.scratchcard.data.repository

import com.example.scratchcard.data.mapper.toScratchCard
import com.example.scratchcard.data.remote.api.CardApiService
import com.example.scratchcard.domain.model.ScratchCard
import com.example.scratchcard.domain.repository.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val api: CardApiService
) : CardRepository {

    override suspend fun activateCode(code: String): ScratchCard {
        return api.activate(code).toScratchCard(code)
    }
}