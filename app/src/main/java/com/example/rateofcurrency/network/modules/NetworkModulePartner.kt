package com.example.rateofcurrency.network.modules

import com.example.rateofcurrency.network.apiservice.NetworkApiServicePartner
import com.example.rateofcurrency.network.apiservice.NetworkApiServicePartner.Companion.BASE_URL_PARTNER
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.create

class NetworkModulePartner {
    private val baseUrl = BASE_URL_PARTNER  // Адрес сервера API
    private val contentType = "application/json".toMediaType()  // Медиатайп Json
    private val json = Json {
        prettyPrint = true  // Читабельные отступы при показе json
        ignoreUnknownKeys = true  // Игнорировать неизвестные поля в Json объекте
        isLenient = true
    }
    private val httpClient = OkHttpClient.Builder()
        .protocols(listOf(Protocol.HTTP_1_1))
        .build()

    @ExperimentalSerializationApi
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(httpClient)
        .build()

    @ExperimentalSerializationApi
    val networkApiService: NetworkApiServicePartner = retrofitBuilder.create()
}

class MyInterceptor : Interceptor {
    /**Перехватывает ответ на запрос клиентской карточкит биллинга и удаляет из него []
     * для корректной десериализации */

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response = chain.proceed(request)
        val body = response.body

        if (body != null) {
            println("!!!!!!!!!!!!!!!${body.string()}")
        }

        return response

    }
}