package com.example.rateofcurrency.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    @SerialName("address")
    val address: String,
    @SerialName("balance")
    val balance: Double,
    @SerialName("city")
    val city: String,
    @SerialName("country")
    val country: String,
    @SerialName("currency")
    val currency: Int,
    @SerialName("currentTradesCount")
    val currentTradesCount: Int,
    @SerialName("currentTradesVolume")
    val currentTradesVolume: Int,
    @SerialName("equity")
    val equity: Double,
    @SerialName("extensionData")
    val extensionData: ExtensionData = ExtensionData(),
    @SerialName("freeMargin")
    val freeMargin: Double,
    @SerialName("isAnyOpenTrades")
    val isAnyOpenTrades: Boolean,
    @SerialName("isSwapFree")
    val isSwapFree: Boolean,
    @SerialName("leverage")
    val leverage: Int,
    @SerialName("name")
    val name: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("totalTradesCount")
    val totalTradesCount: Int,
    @SerialName("totalTradesVolume")
    val totalTradesVolume: Int,
    @SerialName("type")
    val type: Int,
    @SerialName("verificationLevel")
    val verificationLevel: Int,
    @SerialName("zipCode")
    val zipCode: String
) {
    @Serializable
    class ExtensionData(
    )
}
