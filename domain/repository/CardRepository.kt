package com.example.scratchcard.domain.repository

interface CardRepository {
    suspend fun activateCode(code: String): String
}