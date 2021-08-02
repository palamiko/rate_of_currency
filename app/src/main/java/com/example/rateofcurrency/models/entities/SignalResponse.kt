package com.example.rateofcurrency.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignalResponse(
    @SerialName("ActualTime")
    val actualTime: Long,

    @SerialName("Cmd")
    val cmd: Int,

    @SerialName("Comment")
    val comment: String,

    @SerialName("Id")
    val id: Int,

    @SerialName("Pair")
    val pair: String,

    @SerialName("Period")
    val period: String,

    @SerialName("Price")
    val price: Double,

    @SerialName("Sl")
    val sl: Double,

    @SerialName("Tp")
    val tp: Double,

    @SerialName("TradingSystem")
    val tradingSystem: Int
)
