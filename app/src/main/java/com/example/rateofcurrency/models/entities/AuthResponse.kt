package com.example.rateofcurrency.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    /** Результат аутентификации */

    @SerialName("extensionData")
    val extensionData: ExtensionData,

    @SerialName("result")
    val result: Boolean,

    @SerialName("token")
    val token: String

) {

    @Serializable
    class ExtensionData

    fun isSuccess(): Boolean = if (result) result else false
}

