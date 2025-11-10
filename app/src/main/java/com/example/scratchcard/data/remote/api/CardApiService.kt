package com.example.scratchcard.data.remote.api

import com.example.scratchcard.data.remote.dto.ActivateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CardApiService {
    @GET("version")
    suspend fun activate(@Query("code") code: String): ActivateResponse
}