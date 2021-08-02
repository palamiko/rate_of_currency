package com.example.rateofcurrency.network.modules

import com.example.rateofcurrency.network.apiservice.NetworkApiServicePeanut
import com.example.rateofcurrency.network.apiservice.NetworkApiServicePeanut.Companion.BASE_URL_PEANUT
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.create

class NetworkModulePeanut {
    private val baseUrl = BASE_URL_PEANUT  // Адрес сервера API
    private val contentType = "application/json".toMediaType()  // Медиатайп Json
    private val json = Json {
        prettyPrint = true  // Читабельные отступы при показе json
        ignoreUnknownKeys = true  // Игнорировать неизвестные поля в Json объекте
        isLenient = true
    }
    private val httpClient = OkHttpClient.Builder().protocols(listOf(Protocol.HTTP_1_1)).build()

    @ExperimentalSerializationApi
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)

        .addConverterFactory(json.asConverterFactory(contentType))
        .client(httpClient)
        .build()

    @ExperimentalSerializationApi
    val networkApiServicePeanut: NetworkApiServicePeanut = retrofitBuilder.create()
}



