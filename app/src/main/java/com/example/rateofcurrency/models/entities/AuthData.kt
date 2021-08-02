package com.example.rateofcurrency.models.entities

import kotlinx.serialization.Serializable


@Serializable
data class AuthData(
    /** Вспомогательный клас для авторицаии */

    val login: Int,
    val password: String
)

@Serializable
data class AuthDataRequest(
    val login: Int,
    val token: String
)
