package com.example.rateofcurrency.network.apiservice

import com.example.rateofcurrency.models.entities.AuthData
import com.example.rateofcurrency.models.entities.SignalResponse
import retrofit2.http.*

interface NetworkApiServicePartner {

    @POST(AUTH_URL)
    suspend fun authenticate(@Body authData: AuthData): String?

    @GET(GET_SIGNAL)
    suspend fun getSignalsArchive(
        @Header("passkey") authToken: String,
        @Query("tradingsystem") tradSys:Int=3,
        @Query("pairs") pairs: String,
        @Query("from") from: Long,
        @Query("to") to: Long
    ): ArrayList<SignalResponse>

    companion object {
        const val BASE_URL_PARTNER = "https://client-api.instaforex.com/"

        private const val AUTH_URL = "api/Authentication/RequestMoblieCabinetApiToken"
        private const val GET_SIGNAL = "clientmobile/GetAnalyticSignals/20234561"
    }
}