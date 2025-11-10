package com.example.scratchcard.data.repository

import com.example.scratchcard.data.remote.api.CardApiService
import com.example.scratchcard.domain.repository.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val api: CardApiService
) : CardRepository {

    override suspend fun activateCode(code: String): String {
        return api.activate(code).android
    }
}
