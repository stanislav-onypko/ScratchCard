package com.example.scratchcard.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivateResponse(
    @SerialName("ios")
    val ios: String,
    @SerialName("iosTM")
    val iosTM: String,
    @SerialName("iosRA")
    val iosRA: String,
    @SerialName("iosRA_2")
    val iosRA2: String,
    @SerialName("android")
    val android: String,
    @SerialName("androidTM")
    val androidTM: String,
    @SerialName("androidRA")
    val androidRA: String
)